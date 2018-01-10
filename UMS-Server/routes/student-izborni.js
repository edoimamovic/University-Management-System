'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'POST',
  path: '/api/student/izborni',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
	  
	  let sql='insert into nastava(akademskaGod, Kurs_id, Student_id, obnova) values (year(now()), ?, ?, ?)';
      
      let query = con.query(sql, [req.query.kursId, req.query.studentId, req.query.obnova], function(err, res){
          if (!err){
            res(result);
          }
      });    
    }
  }
};
