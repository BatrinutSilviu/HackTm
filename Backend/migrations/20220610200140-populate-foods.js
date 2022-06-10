'use strict';
module.exports = {
    async up(queryInterface, Sequelize) {
        return queryInterface.bulkInsert('Foods', [{
            calories: 150,
            name: 'Ceapa',
            proteins: 55,
            carbs: 45,
            fats: 50,
            createdAt: new Date(),
            updatedAt: new Date()
        }]);
    },
    async down(queryInterface, Sequelize) {
        await queryInterface.dropTable('Users');
    }
};
