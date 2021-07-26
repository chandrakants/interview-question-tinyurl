package com.tinyurl.app.utils;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible to include any common/utilities methods that can be used across the
 * application.
 *
 * @author  Chandrakant
 * @version 1.0
 */
public class TinyURLUtils {

    private TinyURLUtils(){}

    private static final Logger logger = LoggerFactory.getLogger(TinyURLUtils.class);

    /**
     * Utility to validate if the url is correct
     * @param url input url to validate
     * @return true if url is valid false if its not valid
     */
    public static boolean isURLValid(String url) {

        // Check to see if URL is null then return false
        if(StringUtilities.isEmptyOnTrim(url)){
            logger.info("URL is blank.");
            return false;
        }
        UrlValidator validator = new UrlValidator(new String[]{"http","https"});
        return !validator.isValid(url);
    }

}
