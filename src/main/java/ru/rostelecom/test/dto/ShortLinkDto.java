package ru.rostelecom.test.dto;

import lombok.Getter;

@Getter
public class ShortLinkDto {
    private final String shortLink;

    public ShortLinkDto(final String host, final String link) {
        shortLink = "%s/?url=%s".formatted(host, link);
    }
}
