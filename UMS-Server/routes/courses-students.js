'use strict';

const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

module.exports = {
  method: 'GET',
  path: '/api/courses/students',
  config: {
    handler: (req, res) => {
      let con = mysql.createConnection(connection);
      
      //let sql = 'SELECT k.ime, k.prezime FROM nastava ns, kurs ks, student s, korisnik k WHERE ns.student_id = s.id AND ns.kurs_id = ? and s.korisnik_id = k.id';
      let sql = "SELECT * FROM BP07.UserDetails ud, BP07.Users u, BP07.ZamgerUserDetails zud, BP07.User_Enrollment ue, BP07.LabGroup lg, BP07.Course_department cd, BP07.Course c BP07.AcademicYear ay WHERE "
                  + "WHERE ud.userid = u.id "
                  + "AND zud.userid = u.id "
                  + "AND ue.userid = u.id "
                  + "AND ue.labgroupid = lg.id "
                  + "AND lg.course_departmentid = cd.id "
                  + "AND cd.academicyearid = 23 "
                  + "AND cd.courseid = :courseid";

      let con2 = oracledb.getConnection(oracleConnection, function(err, c)
      {
          c.execute(sql, {courseid: {val: req.payload.course, dir: oracledb.BIND_IN, type: oracledb.INT}}, function(errsql1, result){
              res(result.rows);
              return;
          });
      });


      //let query = con.query(sql, [req.query.course], function(err, result){
      //  if (!err){
      //    res(result);      
      //  }
      //});    
    },

    //auth: {
      //strategy: 'jwt',
      //scope: ['profesor']
    //}
  }
};
