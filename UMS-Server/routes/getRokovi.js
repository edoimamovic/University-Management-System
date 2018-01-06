'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/rokovi',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'select * from ispitnirok where kurs_id = ?';
      //let sql = 'INSERT INTO BP07.Exam VALUES(NULL, :termin, ?, ?)';
      
      let query = con.query(sql, [req.payload.kurs], function(err, result){
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
