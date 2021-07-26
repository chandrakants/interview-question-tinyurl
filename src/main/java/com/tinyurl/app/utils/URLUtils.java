package com.tinyurl.app.utils;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

/**
 * This class is reponsible to include any common/utilities methods that can be used across the
 * application.
 *
 * @author Chandrakant
 * @version 1.0
 */
public class URLUtils {

    private static final Logger logger = LoggerFactory.getLogger(URLUtils.class);

    private URLUtils() {
    }

    /**
     * Encode the input String value to base64
     *
     * @param url input string
     * @return encoded value
     */
    public static String encode(String url) {
        return Base64.getUrlEncoder().encodeToString(url.getBytes());
    }

    /**
     * Decode the input String value
     *
     * @param url input string
     * @return decoded value
     */
    public static String decode(String url) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(url);
        return new String(decodedBytes);
    }

    /**
     * Utility to validate if the url is correct
     *
     * @param url
     * @return
     */
    public static boolean isURLValid(String url) {

        // Check to see if URL is null then return false
        if (StringUtilities.isEmptyOnTrim(url)) {
            logger.info("URL is blank.");
            return false;
        }
        UrlValidator validator = new UrlValidator(new String[]{"http", "https"});

        return !validator.isValid(url);
    }
}
