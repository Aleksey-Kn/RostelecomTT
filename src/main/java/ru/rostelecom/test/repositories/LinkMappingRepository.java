package ru.rostelecom.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rostelecom.test.dto.LinkMapping;

@Repository
public interface LinkMappingRepository extends JpaRepository<LinkMapping, String> {
}
