package kipi.dto

data class AccountRelation(
    val id: Long,
    val foreignId: String,
    val type: AccountType
)
