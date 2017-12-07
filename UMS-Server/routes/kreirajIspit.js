'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'POST',
  path: '/api/ispit',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'INSERT INTO ispitnirok VALUES(NULL, ?, ?, ?, ?)';
      
      let query = con.query(sql, [req.payload.rokPrijave, req.payload.vrijemeOdrzavanja, req.payload.sala, req.payload.kurs], function(err, result){
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
