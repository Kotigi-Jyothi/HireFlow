//✅ Generate .ics file automatically after scheduling an interview.

package com.hireflow.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.hireflow.entity.Interview;
import com.hireflow.service.CalendarInviteService;

@Service
public class CalendarInviteServiceImpl implements CalendarInviteService {

    @Override
    public void generateInterviewInvite(Interview interview) {

        try {

            // Create folder if it doesn't exist
            File folder = new File("uploads/calendar-invites");

            if (!folder.exists()) {
                folder.mkdirs();
            }

            // File name
            File file = new File(
                    folder,
                    "interview-" + interview.getId() + ".ics");

            LocalDateTime start = interview.getScheduledAt();
            LocalDateTime end =
                    start.plusMinutes(interview.getDurationMinutes());

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

            FileWriter writer = new FileWriter(file);

            writer.write("BEGIN:VCALENDAR\n");
            writer.write("VERSION:2.0\n");
            writer.write("BEGIN:VEVENT\n");
            writer.write("SUMMARY:HireFlow Interview\n");
            writer.write("DTSTART;TZID=Asia/Kolkata:"
                    + start.format(formatter) + "\n");

            writer.write("DTEND;TZID=Asia/Kolkata:"
                    + end.format(formatter) + "\n");
            writer.write("DESCRIPTION:Interview Scheduled\n");
            writer.write("LOCATION:HireFlow\n");
            writer.write("END:VEVENT\n");
            writer.write("END:VCALENDAR");

            writer.close();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to generate calendar invite.");
        }
    }
}