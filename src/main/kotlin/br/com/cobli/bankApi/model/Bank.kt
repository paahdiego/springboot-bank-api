package br.com.cobli.bankApi.model

data class Bank(
    val accountNumber: String,
    val trust: Double,
    val transactionFee: Int
)