package com.github.kolomolo.service.openaiclient.service;

import com.github.kolomolo.service.openaiclient.model.dto.ChatMessage;
import com.github.kolomolo.service.openaiclient.model.dto.TranscriptionMessage;
import com.github.kolomolo.service.openaiclient.model.mapper.ChatGPTRequestMapper;
import com.github.kolomolo.service.openaiclient.model.mapper.WhisperTranscriptionRequestMapper;
import com.github.kolomolo.service.openaiclient.model.response.WhisperTranscriptionResponse;
import com.github.kolomolo.service.openaiclient.openaiclient.OpenAIClient;
import com.github.kolomolo.service.openaiclient.openaiclient.OpenAIClientConfig;
import com.github.kolomolo.service.openaiclient.model.request.TranscriptionRequest;
import com.github.kolomolo.service.openaiclient.model.response.ChatGPTResponse;
import com.github.kolomolo.service.openaiclient.model.request.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OpenAIClientService {
    @Value("${openai-service.gpt-model}")
    private String model;
    @Value("${openai-service.audio-model}")
    private String audioModel;
    private final OpenAIClient openAIClient;
    private final OpenAIClientConfig openAIClientConfig;


    public ChatMessage getMessage(ChatRequest chatRequest) {
        var chatGptResponse = chat(chatRequest);
        return ChatMessage.builder()
                .question(chatRequest.getQuestion())
                .answer(chatGptResponse.getChoices().stream()
                        .map(choice -> choice.getMessage().getContent())
                        .collect(Collectors.joining()))
                .build();
    }

    public TranscriptionMessage getTranscription(TranscriptionRequest transcriptionRequest) {
        var transcription = createTranscription(transcriptionRequest);
        return TranscriptionMessage.builder()
                .text(transcription.getText())
                .build();
    }

    public ChatGPTResponse chat(ChatRequest chatRequest){
        return openAIClient.chat(ChatGPTRequestMapper.map(chatRequest, model));
    }

    public WhisperTranscriptionResponse createTranscription(TranscriptionRequest transcriptionRequest){
        return openAIClient.createTranscription(WhisperTranscriptionRequestMapper.map(transcriptionRequest, audioModel));
    }
}
