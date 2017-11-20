'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/courses',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'SELECT * FROM kurs ks, predmet p, uposlenik u, korisnik kn WHERE ks.Predmet_id = p.id AND p.Uposlenik_id = u.id AND u.Korisnik_id = kn.id AND kn.username = ? AND ks.godina = ?';
      
      let query = con.query(sql, [req.query.username, new Date().getFullYear()], function(err, result){
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
