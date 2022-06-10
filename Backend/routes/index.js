var express = require('express');
var router = express.Router();
var dbHelper = require('../bin/dbHelper.js');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/getAllFoods', async function (req, res, next) {
  let sequelize;
  try {
    sequelize = dbHelper.connectToDB();
    // const Food = require(`../models/food`)(sequelize)
    // const results = await Food.findAll();
    const [results] = await sequelize.query("SELECT * FROM Foods");
    res.send(JSON.stringify(results));
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  }
  finally {
    sequelize.close();
  }
});

router.get('/getFood/:id', async function (req, res, next){
  let sequelize;
  const {id} = req.params;
  try {
    sequelize = dbHelper.connectToDB();
    const [results] = await sequelize.query("SELECT * FROM Foods WHERE id=" + id);
    res.send(JSON.stringify(results));
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  }
  finally {
    sequelize.close();
  }
});

router.post('/addFood', async function (req, res, next){
  let sequelize;
  try {
    sequelize = dbHelper.connectToDB();
    const [results] = await sequelize.query("INSERT INTO Foods (calories,name,proteins,fats,carbs) VALUES (" + req.body.calories + ",'gulie',123,123,123)");
    if (results){
      res.send("{'success':1}");
    }
    res.send("{'success':0}");
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  }
  finally {
    sequelize.close();
  }
});

module.exports = router;
