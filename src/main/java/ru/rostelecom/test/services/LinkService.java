package ru.rostelecom.test.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rostelecom.test.dto.LongLinkResponse;
import ru.rostelecom.test.exceptions.LongLinkNotFoundException;
import ru.rostelecom.test.repositories.LinkMappingRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkService {
    private final LinkMappingRepository linkMappingRepository;

    public LongLinkResponse findLongLinkFromShort(final String shortLink) {
        return new LongLinkResponse(linkMappingRepository.findById(shortLink)
                .orElseThrow(LongLinkNotFoundException::new)
                .getLongURL());
    }
}
