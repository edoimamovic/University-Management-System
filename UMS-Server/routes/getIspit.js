'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/ispit',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'select pi.id, pi.bodovi from ispitnirok ir, prijavaispit pi, nastava n WHERE pi.ispitnirok_id = ir.id AND pi.Nastava_id = n.id AND ir.id = ?';
      //let sql = 'INSERT INTO BP07.Exam VALUES(NULL, :termin, ?, ?)';
      
        let query = con.query(sql, [req.query.rok], function(err, result){
          if (!err){
            res(result);            
          }
      });    

    //auth: {
      //strategy: 'jwt',
      //scope: ['profesor']
    //}
  }
}};
