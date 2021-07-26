package com.tinyurl.app.scheduler;

import com.tinyurl.app.model.TinyURL;
import com.tinyurl.app.service.TinyURLService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is responsible to run the schedule job every 30 minutes.
 * Also the change is driven by the property fie.
 *
 * @author Chandrakant
 */
@Component
@RequiredArgsConstructor
public class URLTaskScheduler {

    private static final Logger logger = LoggerFactory.getLogger(URLTaskScheduler.class);

    private final TinyURLService urlService;

    /**
     * Run the scheduler method and delete the expired records from the DB.
     */
    @Scheduled(fixedDelayString = "PT30M")
    public void deleteURLRecords() {
        Date currentDateTime = new Date(System.currentTimeMillis());
        int numRows = 0;
        // Loop through the records and find out if the Expiry Date is after current time.
        for (TinyURL entity: urlService.getAllURLRecords()) {

            if(entity.getExpiryDate()!=null &&
                    currentDateTime.after(entity.getExpiryDate())){
                urlService.purgeRecords(entity);
                numRows++;
            }
        }
        logger.info("TinyURL Scheduler running..Records deleted {}",numRows);
    }
}
