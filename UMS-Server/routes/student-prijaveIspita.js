'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/student/prijaveIspita',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'select pi.id prijavaId, ir.id ispitniRokId, p.naziv nazivPredmeta, ir.rokZaPrijave, ir.vrijemeOdrzavanja, s.naziv sala, '+
					   'ti.opis opisIspita, if(ti.redovni=0, \'Popravni\', \'Redovni\') tipIspita, '+
					   'if(ir.rokZaPrijave<now(), 0, 1) aktuelnost '+
				'from korisnik ko, student st, nastava n, kurs k, ispitnirok ir, predmet p, sala s, tipispita ti, '+
					 'prijavaispit pi '+
				'where ko.id=st.Korisnik_id and st.id=n.Student_id and n.Kurs_id=k.id and ir.Kurs_id=k.id and '+
					  'k.Predmet_id=p.id and ir.id=pi.ispitnirok_id and n.id=pi.Nastava_id and ir.Sala_id=s.id and '+
					  'ir.tip=ti.id and ko.username=? '+ 
				'group by ir.id '+
				'order by ir.rokZaPrijave desc';

      let query = con.query(sql, [req.query.username], function(err, result){
        if (!err){
          res(result);      
        }
      });    
    }
  }
};
