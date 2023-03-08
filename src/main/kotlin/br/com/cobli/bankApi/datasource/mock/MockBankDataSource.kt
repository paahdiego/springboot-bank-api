package br.com.cobli.bankApi.datasource.mock

import br.com.cobli.bankApi.datasource.BankDataSource
import br.com.cobli.bankApi.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {
    val banks = mutableListOf(
        Bank(accountNumber = "123", trust = 3.14, transactionFee = 1),
        Bank(accountNumber = "1234", trust = 2.0, transactionFee = 1),
        Bank(accountNumber = "1235", trust = 2.0, transactionFee = 1),
    )

    override fun retrieveBanks(): Collection<Bank> = banks
    override fun retrieveBank(accountNumber: String): Bank = banks.firstOrNull { it.accountNumber == accountNumber }
        ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")

    override fun createBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber }) {
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already created")
        }
        banks.add(bank)

        return bank
    }

    override fun patchBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull { it.accountNumber == bank.accountNumber } ?: throw NoSuchElementException(
            "Bank with account number ${bank.accountNumber} doesn't exists"
        )

        banks.remove(currentBank)
        banks.add(bank)

        return bank
    }

}