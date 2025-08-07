package com.starbank.teamproject;

import com.starbank.teamproject.configuration.RecommendationsDataSourceConfiguration;
import com.starbank.teamproject.controller.RecommendationsController;
import com.starbank.teamproject.model.RecommendationDTO;
import com.starbank.teamproject.repository.RecommendationsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RecommendationsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RecommendationsController recommendationsController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RecommendationsRepository recommendationsRepository;

    @Autowired
    private RecommendationsDataSourceConfiguration recommendationsDataSourceConfiguration;


    @Test
    void contextLoads() throws Exception {
        assertThat(recommendationsController).isNotNull();
    }

    @Test
    public void getRecommendationTest() throws Exception {
        ResponseEntity<List<RecommendationDTO>> getResponse = restTemplate.exchange("http://localhost:" + port + "/recommendation/" + "cd515076-5d8a-44be-930e-8d4fcb79f42d",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        assertThat(getResponse.getBody()).isNotEmpty();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
