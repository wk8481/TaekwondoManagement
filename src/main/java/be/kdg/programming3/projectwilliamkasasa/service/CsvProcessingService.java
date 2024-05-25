package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Service
public class CsvProcessingService {

    private final Logger logger = LoggerFactory.getLogger(CsvProcessingService.class);
    private final StudentService studentService;

    @Autowired
    public CsvProcessingService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Async
    @CacheEvict(value = "search-students", allEntries = true)
    public void processStudentsCsv(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");

            if (data.length < 2) {
                logger.error("Invalid data format: {}", line);
                continue;
            }

            try {
                String name = data[0].trim();
                LocalDate startDate = LocalDate.parse(data[1].trim(), dateFormatter);
                Integer instructorId = data.length > 2 ? Integer.parseInt(data[2].trim()) : null;

                studentService.addStudent(name, startDate, instructorId);
                logger.info("Added student: {}", name);
            } catch (Exception e) {
                logger.error("Error processing data: {}", line, e);
            }
        }
    }
}

