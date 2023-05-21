package ru.rostelecom.test.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.rostelecom.test.entity.LinkMapping;
import ru.rostelecom.test.exceptions.LongLinkNotFoundException;
import ru.rostelecom.test.init.BaseTest;
import ru.rostelecom.test.repositories.LinkMappingRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LinkServiceTest extends BaseTest {
    @Autowired
    private LinkService service;

    @Test
    void findLongLinkFromShortWithExistsShortLink() {
        Mockito.when(repository.findById("xxx"))
                .thenReturn(Optional.of(new LinkMapping("xxx", "https://github.com/")));

        assertThat(service.findLongLinkFromShort("xxx"))
                .isEqualTo("https://github.com/");
    }

    @Test
    void findLongLinkFromShortWithNotExistsShortLink() {
        Mockito.when(repository.findById("xxx"))
                .thenThrow(LongLinkNotFoundException.class);

        assertThatThrownBy(() -> service.findLongLinkFromShort("aaaa"))
                .isInstanceOf(LongLinkNotFoundException.class);
    }

    @Test
    void createShortLink() {
        assertThat(service.createShortLink("http://google.com").getShortLink()).hasSize(40);
    }
}