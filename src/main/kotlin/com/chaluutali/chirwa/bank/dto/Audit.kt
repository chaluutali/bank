package com.chaluutali.chirwa.bank.dto

import java.time.Instant
import java.util.*

data class Audit(
    val auditID: UUID,
    val auditorTimestamp: Instant,
    val auditAction: String,
    val accountNumber: String
    )
