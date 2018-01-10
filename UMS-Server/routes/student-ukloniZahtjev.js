'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'POST',
  path: '/api/student/ukloniZahtjev',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
	  
	  let sql='delete from zahtjevzapotvrdu where id=?';
      
      let query = con.query(sql, [req.payload.zahtjevId], function(err, res){
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
