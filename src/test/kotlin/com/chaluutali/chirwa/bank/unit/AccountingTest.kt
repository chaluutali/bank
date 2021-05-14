package com.chaluutali.chirwa.bank.unit

import com.chaluutali.chirwa.bank.dto.Account
import com.chaluutali.chirwa.bank.service.AccountingService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.*
import java.util.*

class AccountingTest {

    private val accountingService: AccountingService = mock(AccountingService::class.java)
    private val testAccount = Account(UUID.randomUUID(),"0987654321","Chaluutali","1234",0.9)

    @ParameterizedTest
    @MethodSource("deposits")
    fun testDeposits(input: Double, expected: Double) {
        `when`(accountingService.deposit(anyDouble())).thenReturn(input + testAccount.accountBalance)
        Assertions.assertEquals(expected, accountingService.deposit(input))
    }

    @ParameterizedTest
    @MethodSource("withdrawals")
    fun testWithdrawals(input: Double, expected: Double) {
        `when`(accountingService.withdraw(anyDouble())).thenReturn(testAccount.accountBalance - input)
        Assertions.assertEquals(expected, accountingService.withdraw(input))
    }

    @Test
    fun testGetBalance() {
        `when`(accountingService.getBalance()).thenReturn(testAccount.accountBalance)
        Assertions.assertEquals(0.9, accountingService.getBalance())
    }

    @Test
    fun testGetAccount() {
        `when`(accountingService.getAccount(anyString())).thenReturn(testAccount)
        Assertions.assertEquals("0987654321", testAccount.accountNumber)
        Assertions.assertEquals("Chaluutali", testAccount.accountHolderName)
    }

    companion object {
        @JvmStatic
        fun deposits() = listOf(
            Arguments.of(50.0, 50.9),
            Arguments.of(10.0, 10.9)
        )
        @JvmStatic
        fun withdrawals() = listOf(
            Arguments.of(0.1, 0.8),
            Arguments.of(10.0, -9.1)
        )
    }
}