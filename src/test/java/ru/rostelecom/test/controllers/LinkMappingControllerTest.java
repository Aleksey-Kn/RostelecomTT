package ru.rostelecom.test.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.rostelecom.test.dto.ShortLinkDto;
import ru.rostelecom.test.entity.LinkMapping;
import ru.rostelecom.test.init.BaseTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
class LinkMappingControllerTest extends BaseTest {
    @Autowired
    private WebTestClient client;

    @Test
    void findLongLinkFromShortShouldWork(final CapturedOutput output) {
        Mockito.when(repository.findById("123"))
                .thenReturn(Optional.of(new LinkMapping("123", "https://mail.yandex.ru/")));

        callGetLongUrl("123")
                .expectStatus().isEqualTo(302)
                .expectHeader()
                .value("Location", header -> assertThat(header).isEqualTo("https://mail.yandex.ru/"));

        assertThat(output)
                .contains("Got a short link: 123");
    }

    @Test
    void findLongLinkFromShortShouldReturnBadRequestIfLongUrlNotFound(final CapturedOutput output) {
        final String link = "ccc";

        Mockito.when(repository.findById(link))
                .thenReturn(Optional.empty());

        callGetLongUrl(link)
                .expectStatus().isBadRequest();

        assertThat(output)
                .contains("Not found url: ".concat(link));
    }

    @Test
    void createShortUrl() {
        final var shortLink = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/create")
                        .queryParam("url", "http://google.com")
                        .build())
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(ShortLinkDto.class)
                .returnResult().getResponseBody();

        assertThat(shortLink)
                .satisfies(link -> {
                    assertThat(link)
                            .isNotNull();
                    assertThat(link.getShortLink())
                            .hasSize(40);
                });

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"/localhost/habr/id=123456789"})
    void findLongLinkFromShortShouldReturnBadRequestIfShortUrlNotValid(final String shortUrl) {
        Mockito.when(repository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

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