package kipi.dto

import java.io.Serializable
import java.math.BigDecimal

data class Account(
    val id: Long?,
    val userId: Long,
    val balance: BigDecimal,
    val type: AccountType,
    val colorCode: String,
    val foreignAccountId: String?
) : Serializable
