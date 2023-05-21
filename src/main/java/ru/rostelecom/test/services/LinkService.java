package ru.rostelecom.test.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rostelecom.test.dto.ShortLinkDto;
import ru.rostelecom.test.entity.LinkMapping;
import ru.rostelecom.test.exceptions.LongLinkNotFoundException;
import ru.rostelecom.test.repositories.LinkMappingRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkService {
    @Value("${server-host}")
    private String serverHost;

    private final LinkMappingRepository linkMappingRepository;

    public String findLongLinkFromShort(final String shortLink) {
        return linkMappingRepository.findById(shortLink)
                .orElseThrow(() -> new LongLinkNotFoundException(shortLink))
                .getLongURL();
    }

    @Transactional
    public ShortLinkDto createShortLink(final String longLink) {
        String shortUrl;
        do {
            shortUrl = UUID.randomUUID().toString().replace("-", "").substring(0, 13);
        } while (linkMappingRepository.existsById(shortUrl));

        linkMappingRepository.save(new LinkMapping(shortUrl, longLink));

        return new ShortLinkDto(serverHost, shortUrl);
    }
}
