package kipi.controllers

import kipi.dto.AccountDraft
import kipi.dto.ElementCreatedResponse
import kipi.services.AccountService

class AccountCreateController(
    private val accountService: AccountService
) {
    fun handle(userId: Long, accountDraft: AccountDraft) =
        ElementCreatedResponse(accountService.createAccount(userId, accountDraft))
}