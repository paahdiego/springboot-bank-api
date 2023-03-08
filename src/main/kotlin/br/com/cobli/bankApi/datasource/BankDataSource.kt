package br.com.cobli.bankApi.datasource

import br.com.cobli.bankApi.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>

    fun retrieveBank(accountNumber: String): Bank

    fun createBank(bank: Bank): Bank

    fun patchBank(bank: Bank): Bank
}