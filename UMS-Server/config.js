const mysql = require('mysql');

const key = 'tajnikljuc';
const mysqlConnection = {
    host     : '127.0.0.1',
    user     : 'root',
    password : '',
    database : 'mydb'
};

module.exports = {
    key: key,
    mysqlConnection: mysqlConnection
}
