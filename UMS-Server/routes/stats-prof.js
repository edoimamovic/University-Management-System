'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/stats/profesor',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);

      let sqlbrojstudenata = "SELECT COUNT(*) AS broj, p.naziv AS predmet FROM kurs k, predmet p, nastava n, uposlenik u, korisnik ko, student s WHERE k.Predmet_id = p.id AND n.Student_id = s.id AND n.Kurs_id = k.id AND p.Uposlenik_id = u.id AND u.Korisnik_id = ko.id AND ko.username = ? GROUP BY p.id"
      let sqlprosjeci = "SELECT AVG(n.ocjena) AS prosjek, p.naziv AS predmet FROM nastava n, predmet p, kurs k, korisnik ko, uposlenik u  WHERE n.Kurs_id = k.id AND k.Predmet_id = p.id AND p.Uposlenik_id = u.id AND u.Korisnik_id = ko.id AND ko.username = ? GROUP BY p.id";
      let sqlbodovi = "SELECT AVG(pi.bodovi) AS prosjek, p.naziv AS predmet, t.redovni, t.opis AS tip FROM tipispita t, nastava n, ispitnirok ir, predmet p, kurs k, korisnik ko, uposlenik u, prijavaispit pi WHERE n.Kurs_id = k.id AND k.Predmet_id = p.id AND p.Uposlenik_id = u.id AND u.Korisnik_id = ko.id AND ko.username = ? AND ir.tip = t.id AND ir.id = pi.ispitnirok_id and n.id = pi.Nastava_id GROUP BY p.id, t.id, t.redovni";
      
      let query1 = con.query(sqlbrojstudenata, [req.query.username], function(err1, result1){
        if (!err1){
        let query2 = con.query(sqlprosjeci, [req.query.username], function(err2, result2){
          if (!err2){
            let query3 = con.query(sqlbodovi, [req.query.username], function(err3, result3){
              res({brojstudenata: result1, prosjeci: result2, bodovi: result3});
            });
          }
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
