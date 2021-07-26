package com.tinyurl.app.service;

import com.tinyurl.app.base.BaseComponent;
import com.tinyurl.app.model.TinyURL;
import com.tinyurl.app.repository.TinyURLRepository;
import com.tinyurl.app.utils.URLUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * This service class is responsible to perform the service related interaction with the
 * repository (DB layer).
 *
 * @author Chandrakant
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class TinyURLService {

    TinyURLRepository urlRepository;
    BaseComponent baseComponent;

    /**
     * This method is responsible to save the input values/URL to the database.
     * Also responsible to generate the tin URL using base62 algorithm.
     *
     * @param urlToShorten input url
     * @return url
     */
    public String getTinyURL(String urlToShorten, String urlExpiryTime) {
        TinyURL url = new TinyURL();
        url.setUrl(URLUtils.encode(urlToShorten));
        url.setCreationDate(new Date());
        url.setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(Long.parseLong(urlExpiryTime))));
        TinyURL save = urlRepository.save(url);

        return baseComponent.encode(save.getId());
    }

    /**
     * This class does the following
     * 1. Checks the input value and creates the decoded URL from input URL
     * 2. Checks to see if the id retrieved from #1 is present in the Database
     * 3. If #2 fails then return the error response.
     * 4. If #2 finds the URL in the DB then it checks if the URL is expired or not
     *     if No - updates the new expiry date in the Expiry Date column and returns the URL
     *     if Yes- Means the URL is expired then
     *         i.  Delete the entry from the DB
     *         ii. Return the URL expired message.
     * 5. Finally the decoded URL from the ID is returned back to the caller.
     * @param shortUrl
     * @return
     */
    public String getFullUrlFrom(String shortUrl, String urlExpiryTime) {
        long id = baseComponent.decode(shortUrl);
        Optional<TinyURL> optionalEntity = urlRepository.findById(id);

        if (!optionalEntity.isPresent()) {
            return "URL not found in DB";
        }
        TinyURL entity = optionalEntity.get();
        if (isEntityActive(entity)) {
            entity.setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(Long.parseLong(urlExpiryTime))));
            urlRepository.save(entity);
        } else {
            urlRepository.delete(entity);
            return "URL is expired!";
        }
        return URLUtils.decode(entity.getUrl());
    }

    /**
     * Check to see if the current row is expired based on the current date
     *
     * @param entity
     * @return
     */
    private boolean isEntityActive(TinyURL entity) {
        return entity.getExpiryDate() != null &&
                entity.getExpiryDate().after(new Date(System.currentTimeMillis())) ||
                entity.getExpiryDate().equals(new Date(System.currentTimeMillis()));
    }

    /**
     * This method returns all the records from the DB
     * @return List of URL's
     */
    public List<TinyURL> getAllURLRecords() {
        List<TinyURL> urls = new ArrayList<>();
        urlRepository.findAll().forEach(url -> urls.add(url));
        return urls;
    }

    /**
     * This method is responsible to delete the records from the DB
     * @param entity
     */
    public void purgeRecords(TinyURL entity) {
        urlRepository.delete(entity);
    }
}
