package me.chulgil.msa.banking.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFirmbankingRequest {
    private String firmbankingRequestAggregateIdentifier;
    private int status;
}
