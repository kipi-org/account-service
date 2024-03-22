package kipi

import kipi.controllers.*
import kipi.repositories.AccountRepository
import kipi.services.AccountService

class Dependencies {
    val config = Config()
    private val accountRepository = AccountRepository()
    private val accountService = AccountService(accountRepository)
    val accountCreateController = AccountCreateController(accountService)
    val accountDeleteController = AccountDeleteController(accountService)
    val allAccountsDeleteController = AllAccountsDeleteController(accountService)
    val accountsFindController = AccountsFindController(accountService, config)
    val foreignAccountsCreateController = ForeignAccountsCreateController(accountService)
}