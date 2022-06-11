function connectToDB()
{
    const {Sequelize} = require('sequelize');

    const sequelize = new Sequelize('hacktm', 'root', 'root', {
        host: 'localhost',
        dialect: 'mysql'
    });
    sequelize.authenticate();

    return sequelize;
}

module.exports = { connectToDB };
