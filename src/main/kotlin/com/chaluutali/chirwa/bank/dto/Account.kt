package com.chaluutali.chirwa.bank.dto

import lombok.Builder
import java.util.*
import javax.persistence.*

@Builder
@Entity
@Table(name = "account", schema = "bank")
data class Account(
        @Id
        val accountID: UUID,
        val accountNumber: String,
        val accountHolderName: String,
        val password: String,
        var accountBalance: Double = 0.0
)
