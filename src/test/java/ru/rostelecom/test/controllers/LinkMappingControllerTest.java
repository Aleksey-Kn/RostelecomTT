package ru.rostelecom.test.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.rostelecom.test.init.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
class LinkMappingControllerTest extends BaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findLongLinkFromShortWithCorrectValue(final CapturedOutput output) {
        mockMvc.perform(get("/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.longLink").value("https://mail.yandex.ru/"));

        assertThat(output)
                .contains("Got a short link: 123");
    }

    @Test
    @SneakyThrows
    void findLongLinkFromShortWithNotExistValue(final CapturedOutput output) {
        mockMvc.perform(get("/ccc"))
                .andExpect(status().isBadRequest());

        assertThat(output)
                .contains("Not found long link");
    }
}