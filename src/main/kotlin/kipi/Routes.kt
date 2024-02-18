package kipi

import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kipi.dto.AccountDraft

fun Application.routes(deps: Dependencies) = with(deps) {
    routing {
        get("/health") {
            call.respond(OK)
        }

        route("/customer/{userId}") {
            post<AccountDraft> {
                call.respond(OK, accountCreateController.handle(call.userId, it))
            }

            post<List<AccountDraft>>("/foreign") {
                val relations = foreignAccountsCreateController.handle(call.userId, it)
                call.respond(OK, relations)
            }

            get("/accounts") {
                call.respond(OK, accountsFindController.handle(call.userId))
            }

            delete("/account/{accountId}") {
                call.respond(OK, accountDeleteController.handle(call.userId, call.accountId))
            }
        }
    }
}

private val ApplicationCall.userId: Long
    get() = this.parameters.getOrFail("userId").toLong()

private val ApplicationCall.accountId: Long
    get() = this.parameters.getOrFail("accountId").toLong()
