package ru.rostelecom.test.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.rostelecom.test.init.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
class LinkMappingControllerTest extends BaseTest {
    @Autowired
    private WebTestClient client;

    @Test
    void findLongLinkFromShortShouldWork(final CapturedOutput output) {
        callGetLongUrl("123")
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.longLink", "https://mail.yandex.ru/");

        assertThat(output)
                .contains("Got a short link: 123");
    }

    @Test
    void findLongLinkFromShortShouldReturnBadRequestIfLongUrlNotFound(final CapturedOutput output) {
        callGetLongUrl("ccc")
                .expectStatus().isBadRequest();

        assertThat(output)
                .contains("Not found long link");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"/localhost/habr/id=123456789"})
    void findLongLinkFromShortShouldReturnBadRequestIfShortUrlNotValid(final String shortUrl) {
        callGetLongUrl(shortUrl)
                .expectStatus().isBadRequest();
    }

    private WebTestClient.ResponseSpec callGetLongUrl(final String shortUrl) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/")
                        .queryParam("url", shortUrl)
                        .build())
                .exchange();
    }
}