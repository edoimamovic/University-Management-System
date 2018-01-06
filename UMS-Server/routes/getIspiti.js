'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/ispiti',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'SELECT * FROM ispitnirok ir, uposlenik u, kurs k, predmet p, korisnik ko WHERE ir.Kurs_id = k.id AND k.Predmet_id = p.id AND p.Uposlenik_id = u.id AND u.Korisnik_id = ko.id AND ko.username = ?';
      //let sql = 'INSERT INTO BP07.Exam VALUES(NULL, :termin, ?, ?)';
      
      let query = con.query(sql, [req.query.username], function(err, result){
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
