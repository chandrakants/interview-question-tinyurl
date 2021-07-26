package com.tinyurl.app.controller;

import com.tinyurl.app.service.TinyURLService;
import com.tinyurl.app.utils.StringUtilities;
import com.tinyurl.app.utils.URLUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * This is an main controller class for Tiny URL interview question.
 * The main purpose of the class is
 *   1. Create a tiny URL from full URL
 *   2. Create a full URL from tiny URL *
 * @author Chandrakant
 */
@RestController("URLControllerV1")
@RequiredArgsConstructor
public class TinyURLController {

    private static final Logger logger = LoggerFactory.getLogger(TinyURLController.class);
    private final TinyURLService urlService;

    @Value("${app.url.expiry}")
    public String expiry;

    /**
     * Method to create a tiny URL from the long URL provided in the URL path
     * @param url request param
     * @param request servlet request
     * @return ResponseEntity of success or Failure
     */
    @PostMapping("/short")
    public ResponseEntity<String> createTinyURL(@RequestParam("url") String url, HttpServletRequest request) {

        // Check if the URL is valid
        if(URLUtils.isURLValid(url) || StringUtilities.isEmpty(url)) {
            return new ResponseEntity<>("Unable to create URL for now. Please try again later.", HttpStatus.BAD_REQUEST);
        }
        // Call the method to process the request and create a tiny URL
        String tinyURL  = urlService.getTinyURL(url, expiry);
        // Return the tiny URL created back to the caller.
        return new ResponseEntity<>(request.getRequestURL().toString().replace("short","long?tiny=".concat(tinyURL)), HttpStatus.OK);
    }

    /**
     * This method is responsible for retrieving the full URL from the DB.
     * If there is an error then returns the error back to the caller or else
     * returns the successfully created URL back to the caller.
     *
     * @param tiny
     * @return
     */
    @GetMapping(value = "/long")
    public ResponseEntity<String> getOriginalURLFromTiny(@RequestParam String tiny) {

        // validate if url param tiny is not empty.
        if(StringUtilities.isEmptyOnTrim(tiny)){
            logger.error("Error retrieving the URL from DB");
            return new ResponseEntity<>("URL not found in DB", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(urlService.getFullUrlFrom(tiny, expiry),HttpStatus.OK);
    }
}
