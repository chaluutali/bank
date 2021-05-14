package com.chaluutali.chirwa.bank.integration

import com.chaluutali.chirwa.bank.dto.Account
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.reactive.function.client.WebClient
import org.junit.jupiter.api.Assertions

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountingIntegration(@LocalServerPort private val randomServerPort: Int, @Autowired private var objectMapper: ObjectMapper) {

    private val graphqlClient: GraphQLWebClient = GraphQLWebClient.newInstance(WebClient.builder()
        .baseUrl("http://localhost:$randomServerPort/graphql")
        .build(), objectMapper)

    @Test
    fun test_get_account_balance_query() {
        val response = graphqlClient.post("graphql/test/samples/queries/getBalance.graphql", null, Double::class.java)
        Assertions.assertNotNull(response)
        Assertions.assertEquals(507.9, response.block())
    }

    @Test
    fun test_get_account_query() {
        val response = graphqlClient.post("graphql/test/samples/queries/getAccount.graphql", null, Account::class.java)
        Assertions.assertNotNull(response)
        Assertions.assertEquals("0987654321", response.block()?.accountNumber)
        Assertions.assertEquals("Chaluutali", response.block()?.accountHolderName)
        Assertions.assertEquals(507.9, response.block()?.accountBalance)
    }

    @Test
    fun test_withdraw_mutation() {
        val response = graphqlClient.post("graphql/test/samples/mutations/withdraw.graphql", null, Double::class.java)
        Assertions.assertNotNull(response)
        Assertions.assertEquals(507.7, response.block())
    }

    @Test
    fun test_deposit_mutation() {
        val response = graphqlClient.post("graphql/test/samples/mutations/deposit.graphql", null, Double::class.java)
        Assertions.assertNotNull(response)
        Assertions.assertEquals(507.9, response.block())
    }
}