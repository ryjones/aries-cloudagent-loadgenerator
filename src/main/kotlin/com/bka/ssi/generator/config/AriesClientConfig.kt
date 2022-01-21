/*
 *
 *  * Copyright 2022 Bundesrepublik Deutschland
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.bka.ssi.generator.config

import com.bka.ssi.generator.infrastructure.ariesclient.AcaPyAriesClient
import com.bka.ssi.generator.infrastructure.ariesclient.IAriesClient
import org.hyperledger.aries.AriesClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AriesClientConfig(
    @Value("\${issuer-verifier.acapy.api-key}") private val issuerVerifierAcaPyApiKey: String?,
    @Value("\${issuer-verifier.acapy.url}") private val issuerVerifierAcaPyUrl: String?,
    @Value("\${holder.acapy.api-key}") private val holderAcaPyApiKey: String?,
    @Value("\${holder.acapy.url}") private val holderAcaPyUrl: String?
) {
    var logger: Logger = LoggerFactory.getLogger(AriesClientConfig::class.java)

    @Bean(name = ["IssuerVerifier"])
    fun issuerVerifierAriesClient(): IAriesClient? {
        if (issuerVerifierAcaPyUrl == null) {
            logger.error("Unable to establish connection to Issuer/Verifier AcaPy. Issuer/Verifier AcaPy URL not configured.")
            return null
        }

        val issuerVerifierAcaPyClient =
            AriesClient.builder()
                .url(issuerVerifierAcaPyUrl)
                .apiKey(issuerVerifierAcaPyApiKey)
                .build()

        return AcaPyAriesClient(issuerVerifierAcaPyClient)
    }

    @Bean(name = ["Holder"])
    fun holderAriesClient(): IAriesClient? {
        if (holderAcaPyUrl == null) {
            logger.error("Unable to establish connection to Holder AcaPy. Holder AcaPy URL not configured.")
            return null
        }
        val holderAcaPyClient =
            AriesClient.builder()
                .url(holderAcaPyUrl)
                .apiKey(holderAcaPyApiKey)
                .build()

        return AcaPyAriesClient(holderAcaPyClient)
    }
}