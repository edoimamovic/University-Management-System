'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/odsjek',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'SELECT * FROM odsjek';
      
      let query = con.query(sql, [], function(err, result){
        if (!err){
          res(result);      
        }
      });    
    },

    //auth: {
      //strategy: 'jwt',
      //scope: ['profesor']
    //}
  }
};
