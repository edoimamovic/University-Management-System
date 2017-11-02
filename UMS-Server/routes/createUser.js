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
      let user = {};
      user.username = req.payload.username;
      hashPassword(req.payload.password, (err, hash) => {
        if (err) {
          throw Boom.badRequest(err);
        }

        user.password = hash;
        let con = mysql.createConnection(mysqlConnection);
        con.connect(err, function(){
          let sql = "INSERT INTO korisnici SET ?";
          let query = con.query(sql, user, function(){
            res({ id_token: createToken(user) }).code(201);          
          });
        });
      });
    },
    // Validate the payload against the Joi schema
    validate: {
      payload: createUserSchema
    }
  }
};
