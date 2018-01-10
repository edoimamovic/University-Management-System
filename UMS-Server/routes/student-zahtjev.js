'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'POST',
  path: '/api/student/zahtjev',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
	  
	  let sql='insert into zahtjevzapotvrdu(datumZahtjeva, status, tipPotvrde_id, Student_id) '+
			  'values(now(), 0, ?, ?)';

      let query = con.query(sql, [req.payload.tipPotvrdeId, req.payload.studentId], function(err, res){
          if (!err){
			  // console.log(res);
          }
		  else{
			  console.log(err);
		  }
      });    
    }
  }
};
