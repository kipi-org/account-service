package kipi

import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kipi.db.DataSourceConfigurator
import kipi.db.DbMigration
import org.jetbrains.exposed.sql.Database

fun main() {
    embeddedServer(Netty, port = 7002, host = "0.0.0.0", module = Application::init)
        .start(wait = true)
}

fun Application.init() {
    val mapper = jsonMapper {
        disable(WRITE_DATES_AS_TIMESTAMPS)
        disable(FAIL_ON_UNKNOWN_PROPERTIES)
        serializationInclusion(NON_NULL)
        enable(READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
        addModule(JavaTimeModule())
        addModule(kotlinModule { configure(KotlinFeature.SingletonSupport, true) })
    }
    install(ContentNegotiation) {
        register(Json, JacksonConverter(mapper))
    }

    val deps = Dependencies()

    initMigration()
    routes(deps)
}

private fun initMigration() {
    DataSourceConfigurator().also {
        val dataSource = it.createDataSource()
        Database.connect(dataSource)
        DbMigration(dataSource).migrate()
    }
}