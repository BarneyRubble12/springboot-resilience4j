package com.bedrock.circuitbreaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
class Resilience4jControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @RepeatedTest(10)
  void delayServiceTest(RepetitionInfo repetitionInfo) {
    var delay = 1 + (repetitionInfo.getCurrentRepetition() % 2);

    webTestClient
        .get()
        .uri("/api/delay/{delay}", delay)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isOk();

  }

}
