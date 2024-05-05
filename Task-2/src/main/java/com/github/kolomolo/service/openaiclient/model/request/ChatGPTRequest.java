package com.github.kolomolo.service.openaiclient.model.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ChatGPTRequest implements Serializable {

    private String model;
    private List<Message> messages;
}
