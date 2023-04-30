package ru.rostelecom.test.entity;

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
    @Column(name = "short_url", length = 13)
    private String shortURL;

    @Column(name = "long_url")
    private String longURL;
}
