package com.chaluutali.chirwa.bank.repository

import com.chaluutali.chirwa.bank.dto.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository: CrudRepository<Account,UUID> {
    fun findByAccountNumber(accountNumber: String): Account
}