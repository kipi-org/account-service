package kipi.controllers

import kipi.Config
import kipi.dto.Account
import kipi.dto.AccountType.MAIN
import kipi.services.AccountService

class AccountsFindController(
    private val accountService: AccountService,
    private val config: Config
) {
    fun handle(userId: Long): List<Account> {
        val accounts = ArrayList(accountService.findAccounts(userId))
        accounts.add(
            0,
            Account(
                id = null,
                userId = userId,
                balance = accounts.sumOf { it.balance },
                type = MAIN,
                colorCode = config.defaultMainAccountColor,
                foreignAccountId = null,
            )
        )

        return accounts
    }
}