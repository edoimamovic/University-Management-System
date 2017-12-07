'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'POST',
  path: '/api/predmet',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      var godina = (new Date()).getFullYear();
      let predmetsql = 'INSERT INTO predmet VALUES(NULL, ?, ?, ?, ?)';
      let kurssql = 'INSERT INTO kurs VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?)';
      
      let query = con.query(predmetsql, [req.payload.naziv, req.payload.sifra, req.payload.opis, req.payload.profesor], function(err, res1){
          if (!err){
            con.query(kurssql, [res1.insertId, godina, req.payload.odsjek, req.payload.izborni, req.payload.brPredavanja, req.payload.brVjezbi, 0, req.payload.ects], function(err, result){
              res(result);  
            });
          }
      });    
    },

    //auth: {
      //strategy: 'jwt',
      //scope: ['profesor']
    //}
  }
};
