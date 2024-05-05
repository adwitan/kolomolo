package com.github.kolomolo.service.openaiclient.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    String token;
}
