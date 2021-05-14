package com.chaluutali.chirwa.bank.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {
    @Bean
    fun restTemplate(): RestTemplate{
        return RestTemplate()
    }
    @Bean
    fun httpHeaders(): HttpHeaders{
        return HttpHeaders()
    }
}