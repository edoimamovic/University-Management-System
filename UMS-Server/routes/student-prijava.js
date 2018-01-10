'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'POST',
  path: '/api/student/prijava',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
	  
	  let sql='insert into prijavaispit (datumPrijave, Nastava_id, ispitnirok_id) '+
			  'value (now(), ?, ?)';
			  
      let query = con.query(sql, [req.payload.nastavaId, req.payload.ispitniRokId], function(err, res){
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
