'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/student/predmet/ocjena',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'select n.ocjena '+
				'from korisnik ko, student st, nastava n '+
				'where ko.id=st.Korisnik_id and st.id=n.Student_id and n.Kurs_id=? and '+
					  'n.ocjena!=5 and ko.username=?';

      let query = con.query(sql, [req.query.kursId, req.query.username], function(err, result){
        if (!err){
          res(result);      
        }
      });    
    }
  }
};
