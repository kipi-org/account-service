package kipi.controllers

import kipi.services.AccountService

class AccountDeleteController(
    private val accountService: AccountService
) {
    fun handle(userId: Long, accountId: Long) = accountService.deleteAccount(userId, accountId)
}