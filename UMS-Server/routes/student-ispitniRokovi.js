'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/student/ispitniRokovi',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'select ir.id ispitniRokId, k.id kursId, p.naziv nazivPredmeta, ir.rokZaPrijave, ir.vrijemeOdrzavanja, s.naziv sala, '+ 
					   'ti.opis opisIspita, if(ti.redovni=0, \'Popravni\', \'Redovni\') tipIspita, '+
					   'if(ir.id in (select ir.id '+
									'from korisnik ko, student st, nastava n, ispitnirok ir, prijavaispit pi '+
									'where ko.id=st.Korisnik_id and st.id=n.Student_id and n.Kurs_id=ir.Kurs_id and '+
										  'ir.id=pi.ispitnirok_id and n.id=pi.Nastava_id and ko.username=? '+
									'group by ir.id), 1, 0) prijavljen, '+
						'if(ir.rokZaPrijave<now(), 0, 1) aktuelnost '+
				'from korisnik ko, student st, nastava n, kurs k, ispitnirok ir, predmet p, sala s, tipispita ti '+
				'where ko.id=st.Korisnik_id and st.id=n.Student_id and n.Kurs_id=k.id and ir.Kurs_id=k.id and '+
					  'k.Predmet_id=p.id and ir.Sala_id=s.id and ir.tip=ti.id and ko.username=? '+
				'order by ir.rokZaPrijave desc';

      let query = con.query(sql, [req.query.username1, req.query.username2], function(err, result){
        if (!err){
          res(result);      
        }
      });    
    }
  }
};
