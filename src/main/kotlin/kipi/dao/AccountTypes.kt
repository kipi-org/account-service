package kipi.dao

import org.jetbrains.exposed.sql.Table

object AccountTypes : Table("acc_types") {
    val id = long("id").autoIncrement("acc_type_id_seq")
    val name = varchar("name", 255)
    override val primaryKey = PrimaryKey(id)
}