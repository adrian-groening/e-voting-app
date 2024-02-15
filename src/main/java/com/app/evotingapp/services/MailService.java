package com.app.evotingapp.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MailService {

    private String email;

    public MailService(String email) {
        this.email = email;
    }

    public boolean checkEmail() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.mailcheck.ai/email/" + email ))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        


        String responseBody = response.body();
        return responseBody.contains("\"disposable\":false");
    }
}
