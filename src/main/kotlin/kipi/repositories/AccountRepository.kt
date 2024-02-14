package kipi.repositories

import kipi.dao.AccountTypes
import kipi.dao.Accounts
import kipi.dto.Account
import kipi.dto.AccountDraft
import kipi.dto.AccountType
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal.ZERO

class AccountRepository {
    fun createAccount(userId: Long, accountDraft: AccountDraft) = transaction {
        Accounts.insert {
            it[Accounts.userId] = userId
            it[type] = AccountTypes.select { AccountTypes.name eq accountDraft.type.toString() }
                .map { row -> row[AccountTypes.id] }.first()
            it[balance] = ZERO
            it[colorCode] = accountDraft.colorCode
            it[foreignAccountId] = accountDraft.foreignAccountId
        }[Accounts.id]
    }

    fun findAccounts(userId: Long): List<Account> = transaction {
        (Accounts innerJoin AccountTypes).select {
            Accounts.userId eq userId
        }.map { mapToAccount(it) }
    }

    fun deleteAccount(accountId: Long, userId: Long) = transaction {
        Accounts.deleteWhere {
            (Accounts.userId eq userId) and (id eq accountId)
        }
    }

    private fun mapToAccount(resultRow: ResultRow): Account =
        Account(
            id = resultRow[Accounts.id],
            userId = resultRow[Accounts.userId],
            balance = resultRow[Accounts.balance],
            type = AccountType.valueOf(resultRow[AccountTypes.name]),
            colorCode = resultRow[Accounts.colorCode],
            foreignAccountId = resultRow[Accounts.foreignAccountId]
        )
}