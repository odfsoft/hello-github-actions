package com.odfsoft.guessgame.config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager
import org.springframework.transaction.ReactiveTransactionManager




@Configuration
class DatabaseConfig(@Value("\${db.hostname}") val host: String,
                     @Value("\${db.username}") val username: String,
                     @Value("\${db.password}") val password: String,
                     @Value("\${db.name}") val name: String) {

    @Bean
    fun connectionFactory(): ConnectionFactory =
        PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host(host)
                .database(name)
                .username(username)
                .password(password)
                .build()
        )

    @Bean
    fun transactionManager(factory: ConnectionFactory): ReactiveTransactionManager {
        return R2dbcTransactionManager(factory)
    }

    @Bean
    fun databaseClient(factory: ConnectionFactory): DatabaseClient {
        return DatabaseClient.builder().connectionFactory(factory).build()
    }

}
