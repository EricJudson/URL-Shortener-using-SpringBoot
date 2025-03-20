package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@RestController
@RequestMapping("/api")
public class UrlShortenerController {
    private final UrlShortenerService service;

    @Autowired
    public UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }
    @RequestMapping("/")
    public String Greet(){
        return "Welcome to My URL Shortener thingy";
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestParam String url) {
        return ResponseEntity.ok(service.shortenUrl(url));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginal(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        String originalUrl = service.getOriginalUrl(shortCode);
        response.sendRedirect(originalUrl);
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }
}
