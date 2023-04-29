package ru.rostelecom.test.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "link_mapping")
public class LinkMapping {
    @Id
    @Column(name = "short_url")
    private String shortURL;

    @Column(name = "long_url")
    private String longURL;
}
