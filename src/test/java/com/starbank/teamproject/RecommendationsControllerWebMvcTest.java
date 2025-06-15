package com.starbank.teamproject;

import com.starbank.teamproject.controller.RecommendationsController;
import com.starbank.teamproject.repository.RecommendationsRepository;
import com.starbank.teamproject.service.RecommendationsService;
import com.starbank.teamproject.model.RecommendationDTO;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendationsController.class)
public class RecommendationsControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationsRepository recommendationsRepository;

    @SpyBean
    private RecommendationsService recommendationsService;

    @InjectMocks
    private RecommendationsController recommendationsController;

    @Test
    void getRecommendation() throws Exception {
        RecommendationDTO recommendation = new RecommendationDTO(UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d"), "TopSaving", "Текст рекомендации");
        List<RecommendationDTO> recommendations = List.of(recommendation);
        when(recommendationsService.getClientRecommendations(UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d"))).thenReturn(recommendations);
        mockMvc.perform(MockMvcRequestBuilders.get("/recommendation/" + "cd515076-5d8a-44be-930e-8d4fcb79f42d")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("cd515076-5d8a-44be-930e-8d4fcb79f42d"))
                .andExpect(jsonPath("$[0].name").value("TopSaving"))
                .andExpect(jsonPath("$[0].text").value("Текст рекомендации"));
    }
}
