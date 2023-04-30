package ru.rostelecom.test.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.rostelecom.test.dto.LongLinkResponse;
import ru.rostelecom.test.exceptions.LongLinkNotFoundException;
import ru.rostelecom.test.services.LinkService;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Конвертация ссылок")
public class LinkMappingController {
    private final LinkService linkService;

    @ExceptionHandler(LongLinkNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void longURLNotFoundExceptionHandler() {
        log.warn("Not found long link");
    }

    @Operation(summary = "Конвертация короткой ссылки в длинную")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Короткая ссылка конвертирована в длинную", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LongLinkResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Указанной короткой ссылки не существует"),
    })
    @GetMapping("/")
    public LongLinkResponse findLongLinkFromShort(@RequestParam("url") @NotBlank @Length(max = 13) final String shortLink) {
        log.info("Got a short link: %s".formatted(shortLink));
        return linkService.findLongLinkFromShort(shortLink);
    }
}
