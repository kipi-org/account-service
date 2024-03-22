package kipi.controllers

import kipi.services.AccountService

class AllAccountsDeleteController(
    private val accountService: AccountService
) {
    fun handle(userId: Long) = accountService.deleteAllAccounts(userId)
}