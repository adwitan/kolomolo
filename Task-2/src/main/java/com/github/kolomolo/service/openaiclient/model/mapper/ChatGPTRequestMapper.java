package com.github.kolomolo.service.openaiclient.model.mapper;


import com.github.kolomolo.service.openaiclient.model.request.ChatGPTRequest;
import com.github.kolomolo.service.openaiclient.model.request.ChatRequest;
import com.github.kolomolo.service.openaiclient.model.request.Message;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ChatGPTRequestMapper {

    public ChatGPTRequest map(ChatRequest chatRequest, String model) {
        return ChatGPTRequest.builder()
                .messages(mapMessages(chatRequest.getQuestion()))
                .model(model)
                .build();
    }

    private List<Message> mapMessages(String question) {
        return List.of( Message.builder()
                .role("user")
                .content(question)
                .build());
    }

}
