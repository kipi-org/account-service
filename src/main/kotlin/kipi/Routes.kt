package kipi

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kipi.dto.AccountDraft

fun Application.routes(deps: Dependencies) = with(deps) {
    routing {
        route("/customer/{userId}") {
            post<AccountDraft> {
                call.respond(HttpStatusCode.OK, accountCreateController.handle(call.userId, it))
            }

            get("/accounts") {
                call.respond(HttpStatusCode.OK, accountsFindController.handle(call.userId))
            }

            delete("/account/{accountId}") {
                call.respond(HttpStatusCode.OK, accountDeleteController.handle(call.userId, call.accountId))
            }
        }
    }
}

private val ApplicationCall.userId: Long
    get() = this.parameters.getOrFail("userId").toLong()

private val ApplicationCall.accountId: Long
    get() = this.parameters.getOrFail("accountId").toLong()
