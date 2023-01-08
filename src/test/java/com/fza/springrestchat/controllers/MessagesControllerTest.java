package com.fza.springrestchat.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fza.springrestchat.SpringRestChatApplication;
import com.fza.springrestchat.models.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fza.springrestchat.repositories.MessagesRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringRestChatApplication.class)
@AutoConfigureMockMvc
public class MessagesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MessagesRepository repository;

    private Messages testMessages;
    private Messages testSavedMessages;
    private final String API = "/messages/";
    private final String API_ID = "/messages/{id}";

    @BeforeEach
    private void generateTestMessages() {


        Messages preparingMessages = Messages.of("text1");
        preparingMessages.setId(1);
        testSavedMessages = preparingMessages;

        testMessages = Messages.of("text1");
    }

    @Test
    @WithMockUser
    public void findAllMessages() throws Exception {
        List<Messages> messages = List.of(
                testSavedMessages
        );

        when(repository.findAll()).thenReturn(messages);

        MvcResult result = mockMvc.perform(get(API))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(messages);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void findMessageById() throws Exception {
        when(repository.findById(1)).thenReturn(Optional.of(testSavedMessages));

        MvcResult result = mockMvc.perform(get(API_ID, 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(testSavedMessages);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void createMessage() throws Exception {
        when(repository.save(testMessages)).thenReturn(testSavedMessages);

        MvcResult result = mockMvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMessages)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(testSavedMessages);

        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void updateMessage() throws Exception {
        when(repository.save(testSavedMessages)).thenReturn(testSavedMessages);
        when(repository.existsById(1)).thenReturn(true);

        mockMvc.perform(put(API_ID, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testSavedMessages)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void deleteMessage() throws Exception {
        doNothing().when(repository).deleteById(1);

        mockMvc.perform(delete(API_ID, 1))
                .andExpect(status().isOk());
    }
}
