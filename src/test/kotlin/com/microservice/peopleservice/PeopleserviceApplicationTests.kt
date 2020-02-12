package com.microservice.peopleservice

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import javax.sql.DataSource


@SpringBootTest
class PeopleserviceApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Bean
    fun dataSource(): DataSource? {
        return Mockito.mock(DataSource::class.java)
    }
}
