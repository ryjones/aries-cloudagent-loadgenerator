package com.bka.ssi.generator.domain

class ProofExchangeRecordDo(
    val id: String,
    val time: Long,
    val connectionId: String,
    val state: String,
    val verifiedAndValid: Boolean
) {
}
