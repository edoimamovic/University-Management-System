'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/courses/students',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'SELECT k.ime, k.prezime FROM nastava ns, kurs ks, student s, korisnik k WHERE ns.student_id = s.id AND ns.kurs_id = ? and s.korisnik_id = k.id';
      
      let query = con.query(sql, [req.query.course], function(err, result){
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
