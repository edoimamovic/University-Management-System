'use strict';

const Boom = require('boom');
const bcrypt = require('bcryptjs');
const mysql = require('mysql');
const connection = require('../config').mysqlConnection;

function verifyUniqueUser(req, res) {
  // Find an entry from the database that
  // matches either the email or username
  let con = mysql.createConnection(connection);
  let sql = 'SELECT * FROM korisnici WHERE username = ?';
  let query = con.query(sql, req.payload.username, function(err, result){
    if (result.length > 0){
      res(Boom.badRequest('Username taken'));
      return;
    }
    res(req.payload);    
  });
}

function verifyCredentials(req, res) {
  const password = req.payload.password;
  let con = mysql.createConnection(connection);
  let sql = 'SELECT password FROM korisnik WHERE username = ?';
  let query = con.query(sql, req.payload.username, function(err, password){
    if (password.length === 0){
      res(Boom.badRequest('Netačna kombinacija usernamea i passworda.'));
      return;
    }

    bcrypt.compare(req.payload.password, password[0].password, (err, isValid) => {
      if (!isValid){
        return res(Boom.badRequest('Netačna kombinacija usernamea i passworda.'));
      }
      res(req.payload);
    });     
  });
}

module.exports = {
  verifyUniqueUser: verifyUniqueUser,
  verifyCredentials: verifyCredentials
};