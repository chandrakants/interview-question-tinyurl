package com.tinyurl.app.controller;


import com.tinyurl.app.model.TinyURL;
import com.tinyurl.app.service.TinyURLService;
import com.tinyurl.app.utils.URLConstants;
import com.tinyurl.app.utils.URLUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TinyURLController.class)
public class URLControllerTest {

    @MockBean
    private TinyURLService urlService;
    @Autowired
    private MockMvc mockMvc;
    private TinyURL url;

    @Autowired
    protected HttpServletRequest httpRequest;

    @BeforeEach
    public void setUp() {
        url = new TinyURL();
        url.setId(new Long(1));
        url.setUrl(URLUtils.encode(URLConstants.URL));
        url.setCreationDate(new Date());
        url.setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(Long.parseLong("30"))));
    }

    @AfterEach
    void tearDown() {
        url = null;
    }

    @Test
    void testGenerateURLValidUrl() throws Exception {
        TinyURL url = new TinyURL();
        url.setUrl(URLUtils.encode("https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json"));
        url.setCreationDate(new Date());
        url.setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)));

        when(urlService.getTinyURL(URLConstants.URL, "30"))
                .thenReturn("sdfsdf");
        mockMvc.perform(post("/short")
                .param("url",URLConstants.URL))
                .andExpect(status().isOk());
    }

    @Test
    void testInvalidRequestParamBadResponse() throws Exception {
        String invalidURL = "sdfsdf://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json";
        TinyURL url = new TinyURL();
        url.setUrl(URLUtils.encode("https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json"));
        url.setCreationDate(new Date());
        url.setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)));

        when(urlService.getTinyURL(URLConstants.URL, "30"))
                .thenReturn("sdfsdf");
        mockMvc.perform(post("/short")
                .param("url",invalidURL))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testBlankRequestParamBadResponse() throws Exception {
        String invalidURL = "sdfsdf://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json";
        TinyURL url = new TinyURL();
        url.setUrl(URLUtils.encode("https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json"));
        url.setCreationDate(new Date());
        url.setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)));

        when(urlService.getTinyURL(URLConstants.URL, "30"))
                .thenReturn("sdfsdf");
        mockMvc.perform(post("/short")
                .param("url",""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testOriginalUrl() throws Exception {
        String urls = "Zmk=";
        TinyURL url = new TinyURL();
        url.setUrl(URLUtils.decode(urls));
        url.setCreationDate(new Date());
        url.setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)));

        when(urlService.getFullUrlFrom("Zmk=", "30"))
                .thenReturn("sdfsdf");
        mockMvc.perform(get("/long")
                .param("tiny","Zmk="))
                .andExpect(status().isOk());
    }

    @Test
    void testOriginalUrlBlank() throws Exception {
        String urls = "";
        TinyURL url = new TinyURL();
        url.setUrl(URLUtils.decode(urls));
        url.setCreationDate(new Date());
        url.setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)));

        when(urlService.getFullUrlFrom("", "30"))
                .thenReturn("sdfsdf");
        mockMvc.perform(get("/long")
                .param("tiny",""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testOriginalInvalidUrl() throws Exception {
        String urls = "sdfsdf";
        TinyURL url = new TinyURL();
        url.setUrl(URLUtils.decode(urls));
        url.setCreationDate(new Date());
        url.setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)));

        when(urlService.getFullUrlFrom("sdfsdf", "30"))
                .thenReturn("sdfsdf");
        mockMvc.perform(get("/long")
                .param("tiny","sdfsdf"))
                .andExpect(status().isOk());
    }

}
