'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'POST',
  path: '/api/student/ukloniPrijavu',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
	  
	  let sql='delete from prijavaispit where id=?';
      
      let query = con.query(sql, [req.payload.prijavaId], function(err, res){
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
