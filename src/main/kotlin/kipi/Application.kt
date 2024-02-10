package kipi

import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kipi.db.DataSourceConfigurator
import kipi.db.DbMigration
import kipi.mappers.JsonMapper.mapper
import org.jetbrains.exposed.sql.Database

fun main() {
    embeddedServer(Netty, port = 7002, host = "0.0.0.0", module = Application::init)
        .start(wait = true)
}

fun Application.init() {
    install(ContentNegotiation) {
        register(Json, JacksonConverter(mapper))
    }

    val deps = Dependencies()

    initMigration(deps.config)
    routes(deps)
}

private fun initMigration(config: Config) {
    DataSourceConfigurator(config).also {
        val dataSource = it.createDataSource()
        Database.connect(dataSource)
        DbMigration(dataSource).migrate()
    }
}