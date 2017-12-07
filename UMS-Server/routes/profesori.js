'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/profesori',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'SELECT k.id, k.ime, k.prezime, u.id FROM korisnik k, uposlenik u, tipuposlenika tu WHERE u.korisnik_id = k.id AND u.tipuposlenika_id = tu.id AND tu.Naziv = \'Profesor\'';
      
      let query = con.query(sql, [], function(err, result){
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
