'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/tipoviPotvrda',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'select * from tippotvrde';

      let query = con.query(sql, [], function(err, result){
        if (!err){
          res(result);      
        }
      });    
    }
  }
};
