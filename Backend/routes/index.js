var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/test', function(req, res, next) {
  console.log("intra");
  const { Sequelize } = require('sequelize');

  const sequelize = new Sequelize('hacktm', 'root', 'root', {
    host: 'localhost',
    dialect: 'mysql'
  });

  try {
    sequelize.authenticate();
    console.log('Connection has been established successfully.');
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  }
  sequelize.close();
  res.render('index', { title: 'Alo' });
});

module.exports = router;
