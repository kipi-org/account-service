package kipi.dao

import org.jetbrains.exposed.sql.Table

object Accounts : Table("accounts") {
    val id = long("id").autoIncrement("account_id_seq")
    val userId = long("userId")
    val balance = decimal("balance", 18, 2)
    val type = reference("accTypeId", AccountTypes.id)
    val colorCode = varchar("colorCode", 255)
    val foreignAccountId = varchar("foreignAccountId", 255).nullable()
    override val primaryKey = PrimaryKey(id)
}