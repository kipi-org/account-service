package kipi.controllers

import kipi.services.AccountService

class AccountsFindController(
    private val accountService: AccountService
) {
    fun handle(userId: Long) = accountService.findAccounts(userId)
}