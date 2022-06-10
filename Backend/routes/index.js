var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/test', async function (req, res, next) {
  const {Sequelize} = require('sequelize');

  const sequelize = new Sequelize('hacktm', 'root', 'root', {
    host: 'localhost',
    dialect: 'mysql'
  });

  try {
    sequelize.authenticate();
    const [results] = await sequelize.query("SELECT * FROM Foods WHERE id = 1");
    res.send(JSON.stringify(results));
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  }
  sequelize.close();
});



module.exports = router;
