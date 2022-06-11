from random import randrange

from models import Food


class FakeFoodRepository(object):
    def __init__(self) -> None:
        pass

    def get_foods(self) -> list[Food]:
        return [
            Food(id,
                 'name_' + str(id),
                 randrange(0, 30),  # proteins
                 randrange(0, 80),  # carbs
                 randrange(0, 20))  # fat
            for id in range(200)]
