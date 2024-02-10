package kipi

import kipi.controllers.AccountCreateController
import kipi.controllers.AccountDeleteController
import kipi.controllers.AccountsFindController
import kipi.repositories.AccountRepository
import kipi.services.AccountService

class Dependencies {
    val config = Config()
    private val accountRepository = AccountRepository()
    private val accountService = AccountService(accountRepository)
    val accountCreateController = AccountCreateController(accountService)
    val accountDeleteController = AccountDeleteController(accountService)
    val accountsFindController = AccountsFindController(accountService)
}