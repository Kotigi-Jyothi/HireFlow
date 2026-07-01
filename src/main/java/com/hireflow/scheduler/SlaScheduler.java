package com.hireflow.scheduler;

//to check whetehr the candidate completed his assigned task within he tme or not
//if he spent more than SLA daya then ex
//This runs every day at 9 AM.


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hireflow.service.SlaService;

@Component
public class SlaScheduler {

    @Autowired
    private SlaService slaService;

  @Scheduled(cron = "0 0 9 * * ?")
   //@Scheduled(fixedRate = 10000)//Run every 10 seconds
    public void checkSla() {

        slaService.checkSlaBreaches();

    }
}

