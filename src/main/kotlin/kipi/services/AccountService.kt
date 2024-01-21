package kipi.services

import kipi.dto.AccountDraft
import kipi.repositories.AccountRepository

class AccountService(
    private val accountRepository: AccountRepository
) {
    fun createAccount(userId: Long, accountDraft: AccountDraft) = accountRepository.createAccount(userId, accountDraft)

    fun findAccounts(userId: Long) = accountRepository.findAccounts(userId)

    fun deleteAccount(userId: Long, accountId: Long) = accountRepository.deleteAccount(accountId, userId)
}