from flask import Flask, jsonify, request
from models import Food, DayPlan, MealPlan
from optimizer import Optimizer
from repository import FakeFoodRepository

app = Flask(__name__)
food_repo = FakeFoodRepository()
optimizer = Optimizer(food_repo)


@app.route('/diet', methods = ['POST', 'GET'])
def compute_diet():
    req = request.json

    plans = optimizer.compute_lp_solver(
        req['proteins'], req['carbs'], req['fat'], req['days'], req['meals_per_day'])

    return jsonify([plan_to_dto(p) for p in plans])


def plan_to_dto(plan: DayPlan) -> dict:
    return {
        'day': plan.day,
        'meals': [meal_to_dto(m) for m in plan.meals]
    }


def meal_to_dto(meal: MealPlan) -> dict:
    return {
        'meal': meal.meal,
        'foods': [food_to_dto(f) for f in meal.foods]
    }


def food_to_dto(food: Food) -> dict:
    return {
        'id': food.id,
        'name': food.name,
        'proteins': food.proteins,
        'carbs': food.carbs,
        'fat': food.fat
    }


if __name__ == '__main__':
    app.run(debug=True)
