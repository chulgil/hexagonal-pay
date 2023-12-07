package me.chulgil.msa.banking.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.chulgil.msa.banking.domain.RegisteredBankAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterBankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void testRegisterBankAccount() throws Exception {

        //given
        RegisterBankAccountRequest request = new RegisterBankAccountRequest("1",
            "bankName",
            "bankAccountNumber",
            true);

        //when
        RegisteredBankAccount expect = RegisteredBankAccount.generateAccount(
            new RegisteredBankAccount.RegisteredBankAccountId("1"),
            new RegisteredBankAccount.MembershipId("1"),
            new RegisteredBankAccount.BankName("bankName"),
            new RegisteredBankAccount.BankAccountNumber("bankAccountNumber"),
            new RegisteredBankAccount.LinkStatusIsValid(true));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/banking/account/register").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.membershipId").value(expect.getMembershipId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.bankName").value(expect.getBankName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.bankAccountNumber").value(expect.getBankAccountNumber()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.linkedStatusIsValid").value(expect.isLinkedStatusIsValid()));
    }
}