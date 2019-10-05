package com.odfsoft.guessgame.config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

@Configuration
class DatabaseConfig(@Value("\${db.hostname}") val host: String,
                     @Value("\${db.username}") val username: String,
                     @Value("\${db.password}") val password: String,
                     @Value("\${db.name}") val name: String) : AbstractR2dbcConfiguration() {

    override fun connectionFactory(): ConnectionFactory =
        PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host(host)
                .database(name)
                .username(username)
                .password(password)
                .build()
        )
}
