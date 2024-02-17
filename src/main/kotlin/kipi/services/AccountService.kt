package kipi.services

import kipi.dto.AccountDraft
import kipi.dto.AccountType
import kipi.exceptions.AccountNotCreatedException
import kipi.repositories.AccountRepository

class AccountService(
    private val accountRepository: AccountRepository
) {
    fun createAccount(userId: Long, accountDraft: AccountDraft): Long {
        if (accountDraft.type == AccountType.MAIN) {
            throw AccountNotCreatedException("account.type.not.exist")
        }

        return accountRepository.createAccount(userId, accountDraft)
    }

    fun findAccounts(userId: Long) = accountRepository.findAccounts(userId)

    fun deleteAccount(userId: Long, accountId: Long) = accountRepository.deleteAccount(accountId, userId)
}