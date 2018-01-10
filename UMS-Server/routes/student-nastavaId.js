'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/student/nastavaId',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'select n.id '+
				'from korisnik k, student s , nastava n '+
				'where k.id=s.Korisnik_id and s.id=n.Student_id and n.Kurs_id=? and k.username=?';

      let query = con.query(sql, [req.query.kursId, req.query.username], function(err, result){
        if (!err){
			  res(result)
          }
		  else{
			  console.log(err);
		  }
      });    
    }
  }
};
