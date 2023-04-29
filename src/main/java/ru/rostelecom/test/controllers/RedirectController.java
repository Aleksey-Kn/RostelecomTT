package ru.rostelecom.test.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.rostelecom.test.dto.LongLinkResponse;
import ru.rostelecom.test.exceptions.LongLinkNotFoundException;
import ru.rostelecom.test.services.LinkService;

@RestController
@RequiredArgsConstructor
@Log
public class RedirectController {
    private final LinkService linkService;

    @ExceptionHandler(LongLinkNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void longURLNotFoundExceptionHandler() {}

    @GetMapping("/{shortLink}")
    public LongLinkResponse findLongLinkFromShort(@PathVariable String shortLink) {
        log.info("Get short link: ".concat(shortLink));
        return linkService.findLongLinkFromShort(shortLink);
    }
}
