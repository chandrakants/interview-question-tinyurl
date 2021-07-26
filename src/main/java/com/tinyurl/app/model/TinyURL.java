package com.tinyurl.app.model;

import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TinyURL {

    private final String id;
    private final String url;
    private final LocalDateTime created;

    public static TinyURL create(String urlToShorten) {
        final String id = Hashing.murmur3_32().hashString(urlToShorten, StandardCharsets.UTF_8).toString();
        return new TinyURL(id, urlToShorten, LocalDateTime.now());
    }
}
