package com.odfsoft.guessgame

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.ApplicationContextInitializer
import org.testcontainers.containers.PostgreSQLContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.context.ContextConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = [AbstractIntegrationTest.Initializer::class])
abstract class AbstractIntegrationTest {

    @Autowired
    protected var mockMvc: MockMvc? = null

    @Autowired
    protected var objectMapper: ObjectMapper? = null

    companion object {
        val sqlContainer = object : PostgreSQLContainer<Nothing>("postgres:10.7") {
            init {
              withDatabaseName("game")
              withUsername("root")
              withPassword("secret")
            }
        }
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            sqlContainer.start()

            TestPropertyValues.of(
                    "db.hostname=" + sqlContainer.containerIpAddress,
                    "db.username=" + sqlContainer.username,
                    "db.password=" + sqlContainer.password
            ).applyTo(configurableApplicationContext.environment)
        }
    }

}