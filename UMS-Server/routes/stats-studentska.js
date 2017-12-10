'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/stats/studentska',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);

      let sql1ciklus = 'SELECT COUNT(*) as broj, godinaStudija, ciklus FROM STUDENT WHERE ciklus = 0 GROUP BY godinaStudija';
      let sql2ciklus = 'SELECT COUNT(*) as broj, godinaStudija, ciklus FROM STUDENT WHERE ciklus = 0 GROUP BY godinaStudija';
      let sql3 = 'SELECT AVG(n.ocjena) AS prosjek, n.akademskaGod, s.godinaStudija AS godina, o.name AS odsjek FROM nastava n, student s, odsjek o  WHERE s.Odsjek_id = o.id GROUP BY s.godinaStudija, s.Odsjek_id';

      let query1 = con.query(sql1ciklus, [], function(err1, result1){
        if (!err1){
            let query2 = con.query(sql2ciklus, [], function(err2, result2){
              if (!err2){
                let query3 = con.query(sql3, [], function(err3, result3){
                  if (!err3){
                    res({prviciklus: result1, drugiciklus: result2, prosjeci: result3});
                  }
                });
            };
        });
      }});    
    },

    //auth: {
      //strategy: 'jwt',
      //scope: ['profesor']
    //}
  }
};
