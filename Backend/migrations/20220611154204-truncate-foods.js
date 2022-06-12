'use strict';

module.exports = {
  async up(queryInterface, _Sequelize) {
    var [results] = await queryInterface.sequelize.query("SELECT id FROM Foods");
    results.forEach(element => {
      console.log(element.id);
    });
    await queryInterface.bulkDelete('Foods', results);
  },

  async down(_queryInterface, _Sequelize) {
  }
};
