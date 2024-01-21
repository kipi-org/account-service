package kipi.dto

import java.io.Serializable

data class Account(
    val id: Long,
    val userId: Long,
    val type: AccountType,
    val colorCode: String,
    val foreignAccountId: String?
) : Serializable
