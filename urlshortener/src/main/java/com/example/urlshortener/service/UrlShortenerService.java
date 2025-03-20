package com.example.urlshortener.service;

import com.example.urlshortener.model.ShortUrl;
import com.example.urlshortener.repo.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlShortenerService {
    private final ShortUrlRepository repository;
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 6;

    @Autowired
    public UrlShortenerService(ShortUrlRepository repository) {
        this.repository = repository;
    }


    public String shortenUrl(String longUrl) {
        // Check if the URL already exists
        Optional<ShortUrl> existingUrl = repository.findByLongUrl(longUrl);
        if (existingUrl.isPresent()) {
            return existingUrl.get().getShortCode();
        }

        // Generate a unique short code
        String shortCode;
        do {
            shortCode = generateShortCode();
        } while (repository.findByShortCode(shortCode).isPresent());

        // Save to DB
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setLongUrl(longUrl);
        shortUrl.setShortCode(shortCode);
        repository.save(shortUrl);

        System.out.println("Generated Short Code: " + shortCode); // Debugging line

        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        return repository.findByShortCode(shortCode)
                .map(ShortUrl::getLongUrl)
                .orElseThrow(() -> new RuntimeException("URL not found"));
    }

    private String generateShortCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }
        return sb.toString();
    }
}
