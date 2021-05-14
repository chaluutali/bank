package com.chaluutali.chirwa.bank.service

import com.chaluutali.chirwa.bank.dto.Audit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AuditService(@Autowired private var restTemplate: RestTemplate, @Autowired private var httpHeaders: HttpHeaders) {

    fun log(audit: Audit) {
        httpHeaders.contentType = MediaType.valueOf("application/graphql")
        val auditMutation = "mutation {\n" +
                "  addAudit(audit: {\n" +
                "    accountNumber: \""+ audit.accountNumber +"\"\n" +
                "    auditAction:\""+ audit.auditAction +"\"\n" +
                "    auditorTimestamp: \""+ audit.auditorTimestamp +"\"\n" +
                "    auditID: \""+ audit.auditID +"\"\n" +
                "  })\n" +
                "}"
        val request = HttpEntity<String>(auditMutation, httpHeaders)
        val response = restTemplate.postForObject<String>("http://localhost:8081/graphql", request, String::class.java)
    }
}