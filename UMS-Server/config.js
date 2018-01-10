const mysql = require('mysql');

const key = 'tajnikljuc';
const mysqlConnection = {
    host     : 'localhost',
    user     : 'etf_user',
    password : 'etf',
    database : 'mydb'
};

const oracleConnection = {
    user            : 'BP20',
    password        : 'qEHBT2wy',
    //oracle://BP03:o3tUtwdn@80.65.65.66/etflab
    connectString   : '80.65.65.66:1521/etflab.db.lab.etf.unsa.ba'
};

module.exports = {
    key: key,
    mysqlConnection: mysqlConnection,
    oracleConnection: oracleConnection
}
