package org.csuf.cpsc411
//Desirae Prather
//HW 1
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.utils.io.readAvailable
import org.csuf.cpsc411.Dao.Claim.Claim
import org.csuf.cpsc411.Dao.Claim.ClaimDao
import java.util.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    routing{
        this.get("/ClaimService/getAll"){
            val cList = ClaimDao().getAll()
            val respJsonStr = Gson().toJson(cList)

            call.respondText(respJsonStr,status = HttpStatusCode.OK, contentType= ContentType.Application.Json)

        }

        post("/ClaimService/add"){
            val contType = call.request.contentType()
            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead
            val output = ByteArray(dataLength)
            data.readAvailable(output)
            val str = String(output)
            val jsonStr = Gson().fromJson(str, Claim::class.java)
            val cObj = Claim(UUID.randomUUID(),jsonStr.title, jsonStr.date, false)
            ClaimDao().addClaim(cObj)

            println("Http message is using POST method with /post ${contType} ")
            call.respondText("The POST was successfully processed.",status = HttpStatusCode.OK, contentType= ContentType.Text.Plain)
        }


    }
}

