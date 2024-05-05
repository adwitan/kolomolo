package com.github.kolomolo.service.openaiclient.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.kolomolo.service.openaiclient.model.db.Role;
import com.github.kolomolo.service.openaiclient.model.db.User;
import com.github.kolomolo.service.openaiclient.model.request.ChatRequest;
import com.github.kolomolo.service.openaiclient.model.request.TranscriptionRequest;
import com.github.kolomolo.service.openaiclient.model.response.ChatGPTResponse;
import com.github.kolomolo.service.openaiclient.model.response.WhisperTranscriptionResponse;
import com.github.kolomolo.service.openaiclient.openaiclient.OpenAIClient;
import com.github.kolomolo.service.openaiclient.repository.UserRepository;
import com.github.kolomolo.service.openaiclient.security.service.JwtService;
import com.github.kolomolo.service.openaiclient.service.OpenAIClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OpenAIClientControllerSecurityTest {

    public static final String PATH_CHAT = "/api/v1/chat";
    public static final String PATH_TRANSCRIPTION = "/api/v1/transcription";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @MockBean
    private OpenAIClient openAIClient;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        ObjectWriter ow = new ObjectMapper().writer();
        String json = ow.writeValueAsString(new ChatRequest("Hi!"));
        mvc.perform(MockMvcRequestBuilders
                        .post(PATH_CHAT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldAllowAccessToOpenApiAndReturn200() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/login")).andExpect(status().isOk());
    }

    @Test
    public void chatShouldAuthenticateWithTokenAndReturn200() throws Exception {
        //given
        User user = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(Role.USER)
                .build();
        String token = jwtService.generateToken(user);
        ObjectWriter ow = new ObjectMapper().writer();
        String json = ow.writeValueAsString(new ChatRequest("Hi!"));

        //when
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(openAIClient.chat(any())).thenReturn(new ChatGPTResponse());

        //then
        assertNotNull(token);
        mvc.perform(MockMvcRequestBuilders
                .post(PATH_CHAT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void transcriptionShouldAuthenticateWithTokenAndReturn200() throws Exception {
        //given
        User user = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(Role.USER)
                .build();
        String token = jwtService.generateToken(user);
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.mp3", "text/plain", "example xml".getBytes());

        //when
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(openAIClient.createTranscription(any())).thenReturn(new WhisperTranscriptionResponse());

        //then
        assertNotNull(token);
        mvc.perform(MockMvcRequestBuilders
                        .multipart(PATH_TRANSCRIPTION)
                        .file(firstFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}