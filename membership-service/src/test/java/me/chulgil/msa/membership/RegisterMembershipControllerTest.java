package me.chulgil.msa.membership;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.chulgil.msa.membership.adapter.in.web.RegisterMembershipRequest;
import me.chulgil.msa.membership.domain.Membership;
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
public class RegisterMembershipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void testRegisterMembership() throws Exception {
        RegisterMembershipRequest request = new RegisterMembershipRequest("name", "email", "address", false, true);
        Membership expect = Membership.generateMember(
            new Membership.MembershipId("1"),
            new Membership.MembershipName("name"),
            new Membership.MembershipEmail("email"),
            new Membership.MembershipAddress("address"),
            new Membership.MembershipIsValid(true),
            new Membership.MembershipIsCorp(false)
        );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/membership/register/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expect.getName())) // 이름 필드 검증
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(expect.getEmail())) // 이메일 필드 검증
            .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(expect.getAddress())) // 주소 필드 검증
            .andExpect(MockMvcResultMatchers.jsonPath("$.valid").value(expect.isValid())) // isValid 필드 검증
            .andExpect(MockMvcResultMatchers.jsonPath("$.corp").value(expect.isCorp())); // isCorp 필드 검증
    }
}
