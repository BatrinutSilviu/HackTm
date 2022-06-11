const dbHelper = require("./dbUtils");

async function insertFood(req) {
    let sequelize = await dbHelper.connectToDB();
    const [results] = await sequelize.query("INSERT INTO Foods (calories,name,proteins,fats,carbs) VALUES ("+
        req.body.calories + ",'" + req.body.name + "'," + req.body.proteins + "," + req.body.fats + "," + req.body.carbs + ")");
    sequelize.close();

    return results;
}

async function getFood(id){
    let sequelize = await dbHelper.connectToDB();
    const [result] = await sequelize.query("SELECT * FROM Foods WHERE id=" + id);
    sequelize.close();

    return JSON.stringify(result);
}

async function getAllFoods(){
    let sequelize = await dbHelper.connectToDB();
    // const Food = require(`../models/food`)(sequelize)
    // const results = await Food.findAll();
    const [results] = await sequelize.query("SELECT * FROM Foods");
    sequelize.close();

    return JSON.stringify(results);
}

module.exports = { insertFood, getFood, getAllFoods };

