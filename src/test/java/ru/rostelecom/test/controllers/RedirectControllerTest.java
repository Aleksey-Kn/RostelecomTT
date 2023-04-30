package ru.rostelecom.test.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.rostelecom.test.init.BaseTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class RedirectControllerTest extends BaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findLongLinkFromShortWithCorrectValue() {
        mockMvc.perform(get("/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.longLink").value("https://mail.yandex.ru/"));
    }

    @Test
    @SneakyThrows
    void findLongLinkFromShortWithNotExistValue() {
        mockMvc.perform(get("/ccc"))
                .andExpect(status().isBadRequest());
    }
}