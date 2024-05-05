package com.github.kolomolo.service.openaiclient.model.dto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ChatMessage {
    String question;
    String answer;
}
