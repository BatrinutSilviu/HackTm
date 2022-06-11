const dbHelper = require("./dbUtils");

async function insertGoal(req) {
    let sequelize = dbHelper.connectToDB();
    const [results] = await sequelize.query("INSERT INTO Goals (target_calories,proteins,fats,carbs) VALUES ("+
        req.body.target_calories + "," + req.body.proteins + "," + req.body.fats + "," + req.body.carbs + ")");
    sequelize.close();

    return results;
}

async function getGoal(id){
    let sequelize = dbHelper.connectToDB();
    const [result] = await sequelize.query("SELECT * FROM Goals WHERE id=" + id);
    sequelize.close();

    return JSON.stringify(result);
}

module.exports = { insertGoal, getGoal };
