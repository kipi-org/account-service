package kipi

import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.routes(deps: Dependencies) = with(deps) {
    routing {
        get("/health") {
            call.respond(OK)
        }

        route("/customer/{userId}") {
            post {
                call.respond(OK, accountCreateController.handle(call.userId, call.receive()))
            }

            post("/foreign") {
                val relations = foreignAccountsCreateController.handle(call.userId, call.receive())
                call.respond(OK, relations)
            }

            get("/accounts") {
                call.respond(OK, accountsFindController.handle(call.userId))
            }

            delete("/account/{accountId}") {
                accountDeleteController.handle(call.userId, call.accountId)
                call.respond(OK)
            }

            delete {
                allAccountsDeleteController.handle(call.userId)
                call.respond(OK)
            }
        }
    }
}

private val ApplicationCall.userId: Long
    get() = this.parameters.getOrFail("userId").toLong()

private val ApplicationCall.accountId: Long
    get() = this.parameters.getOrFail("accountId").toLong()
