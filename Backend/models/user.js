'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class User extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
    }
  }
  User.init({
    name: DataTypes.STRING,
    height: DataTypes.FLOAT,
    weight: DataTypes.FLOAT,
    goal_id: {
      type: DataTypes.INTEGER,
      references: { model: 'Goals', key: 'id' }
    },
    gender: DataTypes.BOOL,
  }, {
    sequelize,
    modelName: 'User',
  });
  return User;
};
