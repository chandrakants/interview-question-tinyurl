package com.tinyurl.app.service;

import com.tinyurl.app.base.BaseComponent;
import com.tinyurl.app.model.TinyURL;
import com.tinyurl.app.repository.TinyURLRepository;
import com.tinyurl.app.utils.URLConstants;
import com.tinyurl.app.utils.URLUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class URLServiceTest {

    @Mock
    private TinyURLRepository urlRepository;

    @Autowired
    @InjectMocks
    private TinyURLService urlService;
    private TinyURL url1;
    private TinyURL url2;
    List<TinyURL> urlList;

    @Mock
    private BaseComponent baseComponent;


    @BeforeEach
    public void setUp() {
        urlList = new ArrayList<>();
        url1 = new TinyURL();
        url1.setId(new Long(1));
        url1.setUrl(URLUtils.encode(URLConstants.URL));
        url1.setCreationDate(new Date());
        url1.setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(Long.parseLong("30"))));

        url2 = new TinyURL();
        url2.setId(new Long(1));
        url2.setUrl(URLUtils.encode(URLConstants.URL));
        url2.setCreationDate(new Date());
        url2.setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(Long.parseLong("30"))));

        urlList.add(url1);
        urlList.add(url2);
    }

    @AfterEach
    public void tearDown() {
        url1 = url2 = null;
    }

    @Test
    void testReturnTinyURL(){
        when(urlRepository.save(any())).thenReturn(url1);
        urlService.getTinyURL(url1.getUrl(), "30");
        verify(urlRepository,times(1)).save(any());
    }

    @Test
    void testDBNotFoundMessageReturned(){
        String fullUrlFrom = urlService.getFullUrlFrom("text.com", "30");
        assertEquals("URL not found in DB", fullUrlFrom);
    }

    @Test
    void testFullFormURLReturned(){
        String fullUrlFrom = urlService.getFullUrlFrom("Zmk=", "30");
        assertEquals("URL not found in DB", fullUrlFrom);
    }

    @Test
    void testGetAllTheRecords(){
        urlRepository.save(url1);
        when(urlRepository.findAll()).thenReturn(urlList);
        List<TinyURL> urlList1 = urlService.getAllURLRecords();
        assertEquals(urlList1, urlList);
        verify(urlRepository,times(1)).save(url1);
        verify(urlRepository,times(1)).findAll();
    }

}
