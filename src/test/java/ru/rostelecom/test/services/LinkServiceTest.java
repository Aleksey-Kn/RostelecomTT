package ru.rostelecom.test.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rostelecom.test.exceptions.LongLinkNotFoundException;
import ru.rostelecom.test.init.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LinkServiceTest extends BaseTest {
    @Autowired
    private LinkService service;

    @Test
    void findLongLinkFromShortWithExistsShortLink() {
        assertThat(service.findLongLinkFromShort("xxx").longLink())
                .isEqualTo("https://github.com/");
    }

    @Test
    void findLongLinkFromShortWithNotExistsShortLink() {
        assertThatThrownBy(() -> service.findLongLinkFromShort("aaaa"))
                .isInstanceOf(LongLinkNotFoundException.class);
    }
}