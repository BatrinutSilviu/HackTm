from typing import NamedTuple


class Food(NamedTuple):
    id: int
    name: str
    proteins: int
    carbs: int
    fat: int
    # calories -> can be computed from proteins/carbs/fat
    # grams -> not super useful right now
    # ingredients -> missing


class MealPlan(NamedTuple):
    meal: int
    foods: list[Food]


class DayPlan(NamedTuple):
    day: int
    meals: list[MealPlan]
