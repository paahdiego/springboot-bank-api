package br.com.cobli.bankApi.service

import br.com.cobli.bankApi.datasource.BankDataSource
import br.com.cobli.bankApi.datasource.mock.MockBankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BankServiceTest {
    private val dataSource : BankDataSource = mockk(relaxed = true)
    private val bankService = BankService(dataSource)

    @Test
    fun `should call its data source to retrieve banks`() {
        every { dataSource.retrieveBanks() } returns emptyList()

        bankService.getBanks()
        verify(exactly = 1) { dataSource.retrieveBanks() }
    }
}