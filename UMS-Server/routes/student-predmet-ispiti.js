'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/student/predmet/ispiti',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'select ir.vrijemeOdrzavanja, ti.opis opisIspita, '+
					   'if(ti.redovni=0, \'Popravni\', \'Redovni\') tipIspita, pi.bodovi, i.rezultat, '+
					   'if(i.polozen=1, \'DA\', \'NE\') polozen '+
				'from korisnik ko, student st, nastava n, ispitnirok ir, tipispita ti, prijavaispit pi, ispit i '+
				'where ko.id=st.Korisnik_id and st.id=n.Student_id and n.Kurs_id=ir.Kurs_id and n.Kurs_id=? and '+
					  'ir.id=pi.ispitnirok_id and n.id=pi.Nastava_id and ir.tip=ti.id and pi.id=i.prijavaIspit_id and '+
					  'ko.username=? '+
				'group by ir.id';

      let query = con.query(sql, [req.query.kursId, req.query.username], function(err, result){
        if (!err){
          res(result);      
        }
      });    
    }
  }
};
