package org.csuf.cpsc411.Dao

import com.almworks.sqlite4java.SQLiteConnection
import java.io.File

class DataBase constructor (var dbName : String = "") {
    init{
        // create database table
        dbName = "C:\\Users\\desir\\Desktop\\ClaimDB.db"
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        val sqlStmt = "create table if not exists claim (id text, title text, date text, isSolved text)"
        dbConn.exec(sqlStmt)

    }
    companion object{
        private var dbObj:DataBase? = null

        fun getInstance() : DataBase? {
            if (dbObj == null) {
                dbObj = DataBase()
            }
            return dbObj
        }
    }
    fun getDbConnection():SQLiteConnection{
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        return dbConn

    }
}