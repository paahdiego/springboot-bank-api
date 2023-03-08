package br.com.cobli.bankApi.service

import br.com.cobli.bankApi.datasource.BankDataSource
import br.com.cobli.bankApi.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()

    fun getBank(accountNumber: String): Bank = dataSource.retrieveBank(accountNumber)

    fun createBank(bank: Bank): Bank = dataSource.createBank(bank)

    fun patchBank(bank: Bank): Bank = dataSource.patchBank(bank)


}