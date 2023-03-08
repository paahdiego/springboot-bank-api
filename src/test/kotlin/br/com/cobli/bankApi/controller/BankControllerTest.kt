package br.com.cobli.bankApi.controller

import br.com.cobli.bankApi.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc, val objectMapper: ObjectMapper
) {
    val baseUrl: String = "/api/banks"

    @Nested
    @DisplayName("GET api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            mockMvc.get(baseUrl).andDo { print() }.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].accountNumber") {
                    value("123")
                }
            }
        }
    }

    @Nested
    @DisplayName("GET api/banks/{accountNumber}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return all banks`() {
            val accountNumber = 123

            mockMvc.get("$baseUrl/$accountNumber").andDo { print() }.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.trust") { value(3.14) }
            }
        }

        @Test
        fun `should return Not Found if the account number does not exist`() {
            val accountNumber = 909090

            mockMvc.get("$baseUrl/$accountNumber").andDo { print() }.andExpect {
                status { isNotFound() }
            }
        }
    }

    @Nested
    @DisplayName("POST api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PostNewBank {

        @Test
        fun `should add the new bank`() {
            val newBank = Bank("acc123", 31.415, 2)

            val response = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            response.andDo { print() }.andExpect {
                status { isCreated() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(newBank))
                }
            }
        }

        @Test
        fun `should return BAD Request if bank given account number already exists`() {
            val invalidBank = Bank("123", 2.5, 3)

            val response = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            response.andDo { print() }.andExpect {
                status { isBadRequest() }
            }
        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
        @Test
        fun `should update existing bank`() {


            val bankUpdate = Bank("123", 2.0, 5)

            val response = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bankUpdate)
            }

            response.andDo { print() }.andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(bankUpdate))
                }
            }

            mockMvc.get("$baseUrl/${bankUpdate.accountNumber}").andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(bankUpdate))
                }
            }
        }
    }
}