from models import Food


def get_food_calories(food: Food) -> int:
    return get_calories(food.proteins, food.carbs, food.fat)


def get_calories(protein: int, carbs: int, fat: int) -> int:
    return 4 * protein + 4 * carbs + 9 * fat
