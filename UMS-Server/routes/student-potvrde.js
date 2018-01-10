'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/student/potvrde',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      let sql = 'select zp.id zahtjevId, zp.datumZahtjeva, zp.status statusPotvrdeId, tp.naziv tipPotvrde, '+
				'if(zp.status=0, \'Ceka na obradu\', \'Obradjeno\') statusPotvrde '+ 
				'from korisnik ko, student st, zahtjevzapotvrdu zp, tippotvrde tp '+
				'where ko.id=st.Korisnik_id and st.id=zp.Student_id and zp.tipPotvrde_id=tp.id and ko.username=?';

      let query = con.query(sql, [req.query.username], function(err, result){
        if (!err){
          res(result);      
        }
      });    
    }
  }
};
