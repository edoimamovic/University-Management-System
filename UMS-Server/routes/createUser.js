'use strict';

const bcrypt = require('bcryptjs');
const Boom = require('boom');
const createUserSchema = require('../schemas/createUser');
const verifyUniqueUser = require('../util/userFunctions').verifyUniqueUser;
const createToken = require('../util/token');
const mysql = require('mysql');
const mysqlConnection = require('../config').mysqlConnection;

function hashPassword(password, cb) {
  // Generate a salt at level 10 strength
  bcrypt.genSalt(10, (err, salt) => {
    bcrypt.hash(password, salt, (err, hash) => {
      return cb(err, hash);
    });
  });
}

module.exports = {
  method: 'POST',
  path: '/api/users',
  config: {
    auth: false,
    // Before the route handler runs, verify that the user is unique
    pre: [{ method: verifyUniqueUser }],
    handler: (req, res) => {
      let user = req.payload;
      hashPassword(req.payload.password, (err, hash) => {
        if (err) {
          throw Boom.badRequest(err);
        }
        user.password = hash;
        let con = mysql.createConnection(mysqlConnection);
        con.connect(err, function(){
          let sql = "INSERT INTO korisnik VALUES (NULL, ?, ?, ?, ?, ?, ?)";
          let query = con.query(sql, [user.ime, user.prezime, user.jmbg, user.username, user.password, user.uloga], function(err, result){
            if (!err){
              if (user.uloga == '1'){
                let profSql = "INSERT INTO uposlenici VALUES(NULL, ?, 1, 1, 2019-05-08)"
                let profQuery = con.query(sql, [result.insertId], function(err2, result2){
                  if (!err2){
                    res(result2);
                    return;
                  }
                });
              }
              else if (user.uloga == '2'){
                let studSql = "INSERT INTO uposlenici VALUES(NULL, ?, 1, 1, ?, 11111, 2019-05-08, 2020-05-08)"
                let studQuery = con.query(sql, [result.insertId, req.odsjek], function(err2, result2){
                  if (!err2){
                    res(result2);
                    return;
                  }
                });
              }
            
              res(result);          
            }
          });
        });
      });
    }
  }
};
