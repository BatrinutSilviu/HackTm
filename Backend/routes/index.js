var express = require('express');
var router = express.Router();
var foodController = require('../src/FoodController');
var goalController = require('../src/GoalController');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/getAllFoods', async function (req, res, next) {
  try {
    let results = await foodController.getAllFoods();
    res.send(results);
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  }
});

router.get('/getFood/:id',async function (req, res, next){
  try {
    const {id} = req.params;
    let result = await foodController.getFood(id);
    res.send(result);
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  }
});

router.post('/addFood',async function (req, res, next){
  try {
    let results = await foodController.insertFood(req);
    if (results){
      res.send("{'success':1}");
    }
    res.send("{'success':0}");
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  }
});

router.post('/addGoal',async function (req, res, next){
  try {
    let results = await goalController.insertGoal(req);
    if (results){
      res.send("{'success':1}");
    }
    res.send("{'success':0}");
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  }
});

router.get('/getGoal/:id',async function (req, res, next){
  try {
    const {id} = req.params;
    let result = await goalController.getGoal(id);
    res.send(result);
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  }
});

module.exports = router;
