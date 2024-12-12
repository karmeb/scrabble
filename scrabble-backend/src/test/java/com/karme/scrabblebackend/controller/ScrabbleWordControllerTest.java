package com.karme.scrabblebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karme.scrabblebackend.model.ScrabbleWordDto;
import com.karme.scrabblebackend.model.ScrabbleWordInfoDto;
import com.karme.scrabblebackend.service.ScrabbleWordService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ScrabbleWordController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ScrabbleWordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ScrabbleWordService scrabbleWordService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getWordInfo_returnsScrabbleWordInfoDto() throws Exception {
        String getInfoRequestWord = "infoword";
        ScrabbleWordInfoDto scrabbleWordInfoDto = ScrabbleWordInfoDto.builder()
                .word(getInfoRequestWord)
                .isValid(true)
                .points(15)
                .build();

        when(scrabbleWordService.getWordInfo(getInfoRequestWord)).thenReturn(scrabbleWordInfoDto);

        ResultActions response = mockMvc.perform(get("/api/words/infoword/info")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(scrabbleWordInfoDto)));
    }

    @Test
    void addNewWord_returnsCreatedStatus() throws Exception {
        ScrabbleWordDto scrabbleWordDto = new ScrabbleWordDto();
        scrabbleWordDto.setWord("someword");

        doNothing().when(scrabbleWordService).addWord(ArgumentMatchers.any());

        ResultActions response = mockMvc.perform(post("/api/words/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scrabbleWordDto)));
        
        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void handleValidationExceptions_returnsBadRequestStatusToMethodArgumentValidationError() throws Exception {
        ScrabbleWordDto invalidScrabbleWordDto = new ScrabbleWordDto();

        ResultActions response = mockMvc.perform(post("/api/words/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidScrabbleWordDto)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.word", CoreMatchers.is("Word must not be blank")))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void handleValidationExceptions_returnsBadRequestStatusToConstraintValidationError() throws Exception {
        ResultActions response = mockMvc.perform(get("/api/words/not-good/info")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.word", CoreMatchers.is("Word must contain only letters")))
                .andDo(MockMvcResultHandlers.print());

    }

}