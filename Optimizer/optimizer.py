import time
from ortools.linear_solver import pywraplp

from helper import get_calories, get_food_calories
from repository import FakeFoodRepository
from models import *

FOOD_DELTA = 50
MEAL_DELTA = 200
MACRO_DELTA = 5


class Optimizer(object):
    def __init__(self, food_repo: FakeFoodRepository) -> None:
        self.food_repo = food_repo

    def compute_lp_solver(
            self,
            proteins: int,
            carbs: int,
            fat: int,
            days: int,
            meals_per_day: int) -> list[DayPlan]:

        target_calories = get_calories(proteins, carbs, fat)
        print(f'Target calories per day: {target_calories}')

        # query available foods.
        foods = self.food_repo.get_foods()

        # Create the mip solver with the SCIP backend.
        solver = pywraplp.Solver.CreateSolver('SCIP')
        if solver is None:
            raise Exception('SCIP solver is unavailable')

        # Variables.
        # x[f, d, m] = 1 if food f is included in meal m of day d
        #   0 <= a < len(foods)
        #   0 <= d < days
        #   0 <= m < meals_per_day
        x = {}
        for food in foods:
            for day in range(days):
                for meal in range(meals_per_day):
                    x[food.id, day, meal] = solver.IntVar(0.0, 3.0,
                                                          f'x_{food.id}_{day}_{meal}')

        # Constraints.
        # Each item is assigned to at most one meal.
        for food in foods:
            solver.Add(sum(x[food.id, day, meal] for day in range(days)
                           for meal in range(meals_per_day)) <= 3)

        # # The amount of calories per week does not exceed the target per day * days
        solver.Add(sum(x[food.id, day, meal] * get_food_calories(food)
                       for food in foods for day in range(days) for meal in range(meals_per_day)) <= target_calories * days)

        # The amount of calories per day does not exceed the target + delta.
        for day in range(days):
            solver.Add(
                sum(x[food.id, day, meal] * get_food_calories(food) for food in foods for meal in range(meals_per_day)) <= target_calories + FOOD_DELTA)

        # The amount of calories per meal does not exceed target / meals_per_day + meal delta.
        for day in range(days):
            for meal in range(meals_per_day):
                solver.Add(sum(x[food.id, day, meal] * get_food_calories(food)
                               for food in foods) <= target_calories / meals_per_day + MEAL_DELTA)

        # The amount of calories per meal does not exceed target / meals_per_day + meal delta.
        for day in range(days):
            for meal in range(meals_per_day):
                solver.Add(sum(x[food.id, day, meal] * get_food_calories(food)
                               for food in foods) >= target_calories / meals_per_day - MEAL_DELTA)

        # The amount of macros per day is ok
        for day in range(days):
            solver.Add(sum(x[food.id, day, meal] * food.proteins for food in foods for meal in range(
                meals_per_day)) >= proteins - MACRO_DELTA)
            solver.Add(sum(x[food.id, day, meal] * food.proteins for food in foods for meal in range(
                meals_per_day)) <= proteins + MACRO_DELTA)

            solver.Add(sum(x[food.id, day, meal] * food.carbs for food in foods for meal in range(
                meals_per_day)) >= carbs - MACRO_DELTA)
            solver.Add(sum(x[food.id, day, meal] * food.carbs for food in foods for meal in range(
                meals_per_day)) <= carbs + MACRO_DELTA)

            solver.Add(sum(x[food.id, day, meal] * food.fat for food in foods for meal in range(
                meals_per_day)) >= fat - MACRO_DELTA)
            solver.Add(sum(x[food.id, day, meal] * food.fat for food in foods for meal in range(
                meals_per_day)) <= fat + MACRO_DELTA)

        # Objective.
        # Maximize total calories.
        objective = solver.Objective()
        for food in foods:
            for day in range(days):
                for meal in range(meals_per_day):
                    objective.SetCoefficient(
                        x[food.id, day, meal], get_food_calories(food))
        objective.SetMaximization()

        start = time.time()
        solver.SetTimeLimit(5000)
        status = solver.Solve()
        print(f'Finished in {time.time() - start} seconds')

        if status == pywraplp.Solver.OPTIMAL:
            print(f'Total packed value: {objective.Value()}')
        else:
            print('The problem does not have an optimal solution.')

        result: list[DayPlan] = []

        for day in range(days):
            print(f'Day {day}')
            calories_per_day = 0
            protein_per_day = 0
            carb_per_day = 0
            fat_per_day = 0

            meals: list[MealPlan] = []
            for meal in range(meals_per_day):
                print(f'Meal {meal}')

                calories_per_meal = 0
                meal_foods: list[Food] = []

                for food in foods:
                    count = x[food.id, day, meal].solution_value()
                    if count > 0:
                        print(
                            f"Food {count} * [{food.name} proteins: {food.proteins} carbs: {food.carbs} fat: {food.fat}]")
                        calories_per_meal += get_food_calories(food)
                        protein_per_day += food.proteins
                        carb_per_day += food.carbs
                        fat_per_day += food.fat

                        meal_foods.append(food)
                calories_per_day += calories_per_meal

                print(f'calories per meal: {calories_per_meal}')

                meals.append(MealPlan(meal, meal_foods))

            print(f'calories per day: {calories_per_day}')
            print(f'protein per day: {protein_per_day}')
            print(f'carb per day: {carb_per_day}')
            print(f'fat per day: {fat_per_day}')

            result.append(DayPlan(day, meals))

        return result

    # def compute_cp_sat_solver(
    #         proteins: int,
    #         carbs: int,
    #         fat: int,
    #         days: int,
    #         meals_per_day: int):


if __name__ == '__main__':
    # pywrapinit.CppBridge.InitLogging('basic_example.py')
    # cpp_flags = pywrapinit.CppFlags()
    # cpp_flags.logtostderr = True
    # cpp_flags.log_prefix = False
    # pywrapinit.CppBridge.SetFlags(cpp_flags)

    repo = FakeFoodRepository()
    optimizer = Optimizer(repo)

    # compute_lp_solver(100, 100, 100, 3, 3)
    optimizer.compute_lp_solver(175, 200, 55, 2, 3)
