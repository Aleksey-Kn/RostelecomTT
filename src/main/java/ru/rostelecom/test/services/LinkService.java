package ru.rostelecom.test.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rostelecom.test.exceptions.LongLinkNotFoundException;
import ru.rostelecom.test.repositories.LinkMappingRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkService {
    private final LinkMappingRepository linkMappingRepository;

    public String findLongLinkFromShort(final String shortLink) {
        return linkMappingRepository.findById(shortLink).orElseThrow(LongLinkNotFoundException::new).getLongURL();
    }
}
