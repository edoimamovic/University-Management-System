'use strict';

const Boom = require('boom');
const bcrypt = require('bcryptjs');
const mysql = require('mysql');
var oracledb = require('oracledb');
oracledb.outFormat = oracledb.OBJECT;

const connection = require('../config').mysqlConnection;
const oracleConnection = require('../config').oracleConnection;

function verifyUniqueUser(req, res) {
  // Find an entry from the database that
  // matches either the email or username


  let con = mysql.createConnection(connection);
  let sql = 'SELECT * FROM korisnik WHERE username = ?';
  let query = con.query(sql, [req.payload.username], function(err, result){
    if (result.length > 0){
      res(Boom.badRequest('Username taken'));
      return;
    }
    res(req.payload);    
  });
}

function verifyCredentials(req, res) {
  /*let con2 = oracledb.getConnection(oracleConnection, function(err, c)
  {
    console.log("test");
  });*/

  const password = req.payload.password;
  let con = mysql.createConnection(connection);
  let sql = 'SELECT k.password, u.opis AS role FROM korisnik AS k, uloga AS u WHERE k.username = ? AND u.id = k.ulogaId';
  let query = con.query(sql, req.payload.username, function(err, password){
    if (password.length === 0){
      res(Boom.badRequest('Netačna kombinacija usernamea i passworda.'));
      return;
    }

    bcrypt.compare(req.payload.password, password[0].password, (err, isValid) => {
      if (!isValid){
        return res(Boom.badRequest('Netačna kombinacija usernamea i passworda.'));
      }
      res(password[0]);
    });     
  });
}

module.exports = {
  verifyUniqueUser: verifyUniqueUser,
  verifyCredentials: verifyCredentials
};