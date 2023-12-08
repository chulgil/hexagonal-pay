package me.chulgil.msa.banking.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.chulgil.msa.money.adapter.in.web.IncreaseMoneyChangingRequest;
import me.chulgil.msa.money.domain.MoneyChangingRequest;
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
class RegisterMoneyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void testRegisterMoney() throws Exception {

        //given
        IncreaseMoneyChangingRequest request = new IncreaseMoneyChangingRequest("1",
            "bankName",
            "moneyNumber",
            true);

        //when
        MoneyChangingRequest expect = MoneyChangingRequest.generateAccount(
            new MoneyChangingRequest.RegisteredMoneyId("1"),
            new MoneyChangingRequest.MembershipId("1"),
            new MoneyChangingRequest.BankName("bankName"),
            new MoneyChangingRequest.MoneyNumber("moneyNumber"),
            new MoneyChangingRequest.LinkStatusIsValid(true));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/banking/account/register").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.membershipId").value(expect.getTargetMembershipId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.bankName").value(expect.getBankName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.moneyNumber").value(expect.getMoneyNumber()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.linkedStatusIsValid").value(expect.isLinkedStatusIsValid()));
    }
}