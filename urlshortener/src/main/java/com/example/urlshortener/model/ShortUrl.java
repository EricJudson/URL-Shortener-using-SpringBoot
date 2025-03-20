package com.example.urlshortener.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    @Column(nullable = false, unique = true)
    private String longUrl;

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }


    public String getShortCode() {
        return shortCode;
    }

    @Column(nullable = false, unique = true)
    private String shortCode;
}
