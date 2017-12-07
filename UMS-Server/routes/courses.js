'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/courses',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);

      let sql = 'SELECT ks.id, p.naziv, p.opis, kn.ime, kn.prezime FROM kurs ks, predmet p, uposlenik u, korisnik kn WHERE ks.Predmet_id = p.id AND p.Uposlenik_id = u.id AND u.Korisnik_id = kn.id AND ks.godina = ?';
      let paramList = [new Date().getFullYear()];
      if (!!req.query.username){
        sql = 'SELECT ks.id, p.naziv, p.opis, kn.ime, kn.prezime FROM kurs ks, predmet p, uposlenik u, korisnik kn WHERE ks.Predmet_id = p.id AND p.Uposlenik_id = u.id AND u.Korisnik_id = kn.id AND kn.username = ? AND ks.godina = ?';
        paramList = [req.query.username, new Date().getFullYear()]
      }

      let query = con.query(sql, paramList, function(err, result){
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
