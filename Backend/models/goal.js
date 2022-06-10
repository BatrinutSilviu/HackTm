'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Goal extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
    }
  }
  Goal.init({
    target_calories: DataTypes.INTEGER,
    proteins_percent: DataTypes.FLOAT,
    carbs_percent: DataTypes.FLOAT,
    fats_percent: DataTypes.FLOAT
  }, {
    sequelize,
    modelName: 'Goal',
  });
  return Goal;
};