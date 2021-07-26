package com.tinyurl.app.repository;

import com.tinyurl.app.base.BaseComponent;
import com.tinyurl.app.model.TinyURL;
import com.tinyurl.app.utils.URLConstants;
import com.tinyurl.app.utils.URLUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class URLRepositoryTest {
    @Autowired
    private TinyURLRepository repository;

    @MockBean
    private BaseComponent baseComponent;

    private TinyURL url;

    @BeforeEach
    public void setUp() {
        url = new TinyURL();
        url.setId(new Long(1));
        url.setUrl(URLUtils.encode(URLConstants.URL));
        url.setCreationDate(new Date());
        url.setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(Long.parseLong("30"))));
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
        url = null;
    }

    @Test
    public void testUrlRecordIsCreatedAndIdIsReturnedFromRepository(){
        TinyURL url1 = repository.save(url);
        TinyURL urlRecord = repository.findById(url1.getId()).get();
        assertEquals(2, urlRecord.getId());
    }

    @Test
    public void testGivenIdThenShouldReturnURlRecordOfId(){
        TinyURL url1 = repository.save(url);
        TinyURL urlRecord = repository.findById(url1.getId()).get();
        assertEquals(url.getId(), urlRecord.getId());
    }

    @Test
    public void testIdDeleteThenDeleteTheURLRecord(){
        repository.delete(url);
        Optional optional = repository.findById(url.getId());
        assertEquals(Optional.empty(), optional);
    }
}
