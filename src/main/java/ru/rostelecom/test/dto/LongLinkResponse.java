package ru.rostelecom.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Длинная ссылка на требуемый ресурс")
public record LongLinkResponse(String longLink) {
}
