'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/stats/student',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);

      let sqlbodovi = "SELECT AVG(pi.bodovi) AS prosjek, p.naziv AS predmet, t.redovni, t.opis AS tip FROM student s, tipispita t, nastava n, ispitnirok ir, predmet p, kurs k, korisnik ko, prijavaispit pi WHERE n.Student_id = s.id AND n.Kurs_id = k.id AND k.Predmet_id = p.id AND s.Korisnik_id = ko.id AND ko.username = ? AND ir.tip = t.id AND ir.id = pi.ispitnirok_id and n.id = pi.Nastava_id GROUP BY p.id, t.id, t.redovni";
            
      let query1 = con.query(sqlbodovi, [req.query.username], function(err1, result1){
        if (!err1){
          res({bodovi: result1});
        }
      });    
    },

    //auth: {
      //strategy: 'jwt',
      //scope: ['profesor']
    //}
  }
};
