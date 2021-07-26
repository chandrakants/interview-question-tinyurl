package com.tinyurl.app.controller;

import com.tinyurl.app.model.TinyURL;
import com.tinyurl.app.utils.TinyURLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is an main controller class for Tiny URL interview question.
 * The main purpose of the class is
 *   1. Create a tiny URL from full URL
 *   2. Create a full URL from tiny URL *
 * @author Chandrakant
 */
@RestController("URLControllerV1")
public class TinyURLController {

    private static final Logger logger = LoggerFactory.getLogger(TinyURLController.class);
    private Map<String, String> urlMap = new ConcurrentHashMap<>();

    /**
     * Method to create a tiny URL from the long URL provided in the URL path
     *
     * @param url request param
     * @return ResponseEntity of success or Failure
     */
    @PostMapping("/short")
    public ResponseEntity<String> createTinyURL(@RequestParam("url") String url, HttpServletRequest request) {

        // Check if the URL is valid
        if (!TinyURLUtils.isURLValid(url)) {
            return new ResponseEntity<>("Unable to create URL for now. Please try again later.", HttpStatus.BAD_REQUEST);
        }

        final TinyURL urlObj = TinyURL.create(url);
        logger.info("URL id generated = {}", urlObj.getId());
        urlMap.put(urlObj.getId(),url);
        // Return the generated id
        return new ResponseEntity<>(request.getRequestURL().toString().replace("short","long?tiny=".concat(urlObj.getId())), HttpStatus.OK);
    }

    /**
     * Retrieve the original URL from the hash code tiny url.
     * @param tiny
     * @return
     */
    @GetMapping(value = "/long")
    public ResponseEntity<String> getOriginalURLFromTiny(@RequestParam String tiny) {
        String urlRetrieved = urlMap.get(tiny);
        return new ResponseEntity<>(urlRetrieved, HttpStatus.OK);
    }
}
