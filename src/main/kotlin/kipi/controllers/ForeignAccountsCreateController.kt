package kipi.controllers

import kipi.dto.AccountDraft
import kipi.dto.AccountRelation
import kipi.exceptions.AccountNotCreatedException
import kipi.services.AccountService

class ForeignAccountsCreateController(
    private val accountService: AccountService
) {
    fun handle(userId: Long, foreignAccountDrafts: List<AccountDraft>): List<AccountRelation> {
        val accounts = accountService.findAccounts(userId).filterNot { it.id == null }

        foreignAccountDrafts.filterNot {
            accounts.any { acc -> acc.type == it.type && acc.foreignAccountId == it.foreignAccountId }
        }.forEach {
            try {
                accountService.createAccount(userId, it)
            } catch (_: AccountNotCreatedException) {
            }
        }

        return accountService.findAccounts(userId)
            .filter { it.foreignAccountId != null && it.id != null }
            .map {
                AccountRelation(
                    it.id!!,
                    it.foreignAccountId!!,
                    it.type
                )
            }
    }
}