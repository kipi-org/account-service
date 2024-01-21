package kipi.dto

import java.io.Serializable

data class AccountDraft(
    val type: AccountType,
    val colorCode: String,
    val foreignAccountId: String?
) : Serializable
