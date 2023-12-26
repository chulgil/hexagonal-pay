package me.chulgil.msa.banking.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFirmbankingRequest {
    private String firmbankingRequestAggregateIdentifier;
    private int status;
}
