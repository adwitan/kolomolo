package com.github.kolomolo.service.openaiclient.controller;

import com.github.kolomolo.service.openaiclient.model.db.User;
import com.github.kolomolo.service.openaiclient.model.request.ChatRequest;
import com.github.kolomolo.service.openaiclient.model.request.TranscriptionRequest;
import com.github.kolomolo.service.openaiclient.service.AuthenticationService;
import com.github.kolomolo.service.openaiclient.service.OpenAIClientService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final OpenAIClientService openAIClientService;
    private final AuthenticationService authenticationService;


    @GetMapping(value = "/chat")
    public String chat() {
        return "chat";
    }

    @PostMapping(value = "/chat")
    public String chat(ChatRequest chatRequest, Model model) {
        var response = openAIClientService.getMessage(chatRequest);
        model.addAttribute("responseMessage", response);
        return "chat";
    }


    @PostMapping(value = "/transcription")
    public String transcription(TranscriptionRequest transcriptionRequest, Model model) {
        var response = openAIClientService.getTranscription(transcriptionRequest);
        model.addAttribute("responseTranscription", response);
        return "chat";
    }

    @GetMapping("/login")
    public String login(User user, HttpServletResponse response) {
        try {
            String token = authenticationService.authenticate(user).getToken();

            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(100);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);

            response.addCookie(cookie);

            return "redirect:chat";
        } catch (Exception e) {
            return "login";
        }
    }

}