const DB_HOST = process.env.DB_HOST || 'localhost';

async function connectToDB() {
    const { Sequelize } = require('sequelize');

    const sequelize = new Sequelize('hacktm', 'root', 'root', {
        host: DB_HOST,
        dialect: 'mysql'
    });
    await sequelize.authenticate();

    return sequelize;
}

module.exports = { connectToDB };
