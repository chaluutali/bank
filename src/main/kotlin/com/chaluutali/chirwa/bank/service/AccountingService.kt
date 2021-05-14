package com.chaluutali.chirwa.bank.service

import com.chaluutali.chirwa.bank.dto.Account
import com.chaluutali.chirwa.bank.dto.Audit
import com.chaluutali.chirwa.bank.dto.User
import com.chaluutali.chirwa.bank.enum.AccountingAction
import com.chaluutali.chirwa.bank.repository.AccountRepository
import io.leangen.graphql.annotations.GraphQLArgument
import io.leangen.graphql.annotations.GraphQLMutation
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@GraphQLApi
@Service
class AccountingService(@Autowired private val accountRepository: AccountRepository, @Autowired private val auditService: AuditService) {
    //assuming user has logged in successfully.
    private val account = accountRepository.findByAccountNumber("0987654321")

    @GraphQLQuery(name = "getBalance")
    fun getBalance(): Double = account.accountBalance

    @GraphQLQuery(name = "getAccount")
    fun getAccount(@GraphQLArgument(name = "accountNumber") accountNumber: String): Account? {
        if(accountNumber.contentEquals(account.accountNumber)){
            return account
        }
        return null
    }

    @GraphQLMutation(name = "deposit")
    fun deposit(@GraphQLArgument(name = "amount") amount: Double): Double {
        account.accountBalance += amount
        accountRepository.save(account)
        auditService.log(Audit(UUID.randomUUID(), Instant.now(), AccountingAction.DEPOSIT.name, account.accountNumber))
        return account.accountBalance
    }

    @GraphQLMutation(name = "withdraw")
    fun withdraw(@GraphQLArgument(name = "amount") amount: Double): Double {
        account.accountBalance -= amount
        accountRepository.save(account)
        auditService.log(Audit(UUID.randomUUID(), Instant.now(), AccountingAction.WITHDRAW.name, account.accountNumber))
        return account.accountBalance
    }

}