package org.csuf.cpsc411.Dao.Claim

import org.csuf.cpsc411.Dao.Dao
import org.csuf.cpsc411.Dao.DataBase
import java.util.*

class ClaimDao: Dao() {
    fun addClaim(cObj : Claim){
        // get db connection
        val conn = DataBase.getInstance()?.getDbConnection()

        //prepare the sql statement
        sqlSt ="insert into Claim (id, title, date, isSolved) values ('${cObj.id}','${cObj.title}','${cObj.date}','${cObj.isSolved}')  "

        // submit the sql statement
        conn?.exec(sqlSt)
    }

    fun getAll():List<Claim>{
        // get db connection
        val conn = DataBase.getInstance()?.getDbConnection()

        // prepare the sql statement
        sqlSt ="select id,title,date,isSolved from Claim "
        // submit the sql statement
        var claimList : MutableList<Claim> = mutableListOf()
        val st= conn?.prepare(sqlSt)

        //convert into kotlin object format
        while (st?.step()!!){
            val id = st.columnString(0)
            val title = st.columnString(1)
            val date = st.columnString(2)
            val isSolved = st.columnString(3)
            claimList.add(Claim(UUID.fromString(id),title,date,isSolved.toBoolean()))
        }
        return claimList
    }
}