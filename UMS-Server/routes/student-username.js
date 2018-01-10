'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/username/student',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'select s.id '+
				'from korisnik k, student s '+
				'where k.id=s.Korisnik_id and k.username=?';

      let query = con.query(sql, [req.query.username], function(err, result){
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
