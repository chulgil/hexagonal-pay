package me.chulgil.msa.membership.application.saga;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MembershipCreateEvent {
    private String name;
    private String email;
    private String address;
}
