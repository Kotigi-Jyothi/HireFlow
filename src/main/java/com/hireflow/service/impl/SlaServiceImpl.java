package com.hireflow.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hireflow.dto.request.SlaAlertEmailRequestDTO;
import com.hireflow.dto.websocket.SlaBreachDTO;
import com.hireflow.entity.Application;
import com.hireflow.entity.PipelineStage;
import com.hireflow.repository.ApplicationRepository;
import com.hireflow.service.EmailService;
import com.hireflow.service.SlaService;
import org.springframework.messaging.simp.SimpMessagingTemplate;


@Service
public class SlaServiceImpl implements SlaService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private EmailService emailService;
    
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Override
    public void checkSlaBreaches() {

    	System.out.println("SLA Scheduler Running...");
        List<Application> applications =
                applicationRepository.findAll();

        for (Application application : applications) {

            PipelineStage stage =
                    application.getCurrentStage();

            long daysSpent =
                    ChronoUnit.DAYS.between(
                            application.getLastStageChangedAt(),
                            LocalDateTime.now());

            //Avoid sending the same email every time the scheduler runs.
            if (daysSpent > stage.getSlaDays()
                    && !Boolean.TRUE.equals(application.getSlaBreached())) {
                application.setSlaBreached(true);

                applicationRepository.save(application);
                
                SlaAlertEmailRequestDTO dto =
                        new SlaAlertEmailRequestDTO();

                dto.setApplicationId(application.getId());

                emailService.sendSlaAlertEmail(dto);
                
                
                SlaBreachDTO breachDTO = new SlaBreachDTO();

                breachDTO.setApplicationId(application.getId());

                breachDTO.setCandidateName(
                        application.getCandidate().getName()
                );

                breachDTO.setStageName(
                        application.getCurrentStage().getStageName()
                );

                breachDTO.setMessage(
                        "SLA breached for candidate "
                        + application.getCandidate().getName()
                );

                messagingTemplate.convertAndSend(
                        "/topic/dashboard/sla",
                        breachDTO
                );
            }
        }
    }
}