package com.github.kolomolo.service.openaiclient.model.mapper;

import com.github.kolomolo.service.openaiclient.model.request.TranscriptionRequest;
import com.github.kolomolo.service.openaiclient.model.request.WhisperTranscriptionRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WhisperTranscriptionRequestMapper {

    public WhisperTranscriptionRequest map(TranscriptionRequest transcriptionRequest, String model) {
        return WhisperTranscriptionRequest.builder()
                .file(transcriptionRequest.getFile())
                .model(model)
                .build();
    }
}
