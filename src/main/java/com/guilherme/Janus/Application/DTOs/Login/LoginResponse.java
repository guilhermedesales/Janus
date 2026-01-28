package com.guilherme.Janus.Application.DTOs.Login;

// import lombok.AllArgsConstructor;
// import lombok.Getter;

// @Getter
// @AllArgsConstructor
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
