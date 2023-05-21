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
import org.springframework.web.servlet.view.RedirectView;
import ru.rostelecom.test.dto.ShortLinkDto;
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
    void longURLNotFoundExceptionHandler(final LongLinkNotFoundException e) {
        log.warn(e.getMessage());
    }

    @Operation(summary = "Конвертация короткой ссылки в длинную")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Выполнен редирект по длинной ссылке"),
            @ApiResponse(responseCode = "400", description = "Указанной короткой ссылки не существует"),
    })
    @GetMapping("/")
    public RedirectView findLongLinkFromShort(@RequestParam("url") @NotBlank @Length(max = 13) final String shortLink) {
        log.info("Got a short link: %s".formatted(shortLink));
        return new RedirectView(linkService.findLongLinkFromShort(shortLink));
    }

    @Operation(summary = "Создание короткой ссылки")
    @ApiResponse(responseCode = "200", description = "Создана короткая ссылка", content = {
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ShortLinkDto.class))
    })
    @GetMapping("/create")
    public ShortLinkDto createShortLink(@RequestParam("url") final String longLink) {
        log.info("Got a long link: %s".formatted(longLink));
        return linkService.createShortLink(longLink);
    }
}
