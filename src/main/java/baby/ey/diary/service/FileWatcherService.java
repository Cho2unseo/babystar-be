package baby.ey.diary.service;

import baby.ey.diary.entity.Growth;
import baby.ey.diary.entity.Meal;
import baby.ey.diary.entity.Poo;
import baby.ey.diary.entity.Sleep;
import baby.ey.diary.repository.GrowthRepository;
import baby.ey.diary.repository.MealRepository;
import baby.ey.diary.repository.PooRepository;
import baby.ey.diary.repository.SleepRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
public class FileWatcherService {

    private static final Logger logger = LoggerFactory.getLogger(FileWatcherService.class);

    private final GrowthRepository growthRepository;
    private final MealRepository mealRepository;
    private final PooRepository pooRepository;
    private final SleepRepository sleepRepository;
    private final ObjectMapper objectMapper;

    private static final String DIRECTORY_PATH = "C:/Users/smyj0/study/hello-spirng/BE/src/main/java/baby/ey/diary";

    @EventListener(ApplicationReadyEvent.class)
    public void watchDirectory() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(DIRECTORY_PATH);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            Thread watchThread = new Thread(() -> {
                while (true) {
                    WatchKey key;
                    try {
                        key = watchService.take();
                    } catch (InterruptedException e) {
                        logger.error("WatchService interrupted", e);
                        return;
                    }

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                            WatchEvent<Path> ev = (WatchEvent<Path>) event;
                            Path fileName = ev.context();
                            File file = new File(DIRECTORY_PATH + "/" + fileName.toString());

                            if (fileName.toString().equals("analysis_result.json")) {
                                logger.info("New file detected: {}", fileName);
                                try {
                                    processJsonFile(file.getAbsolutePath());
                                } catch (IOException e) {
                                    logger.error("Failed to process JSON file", e);
                                }
                            }
                        }
                    }

                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            });

            watchThread.setDaemon(true);
            watchThread.start();

        } catch (IOException e) {
            logger.error("Failed to initialize WatchService", e);
        }
    }

    @Transactional
    public void processJsonFile(String filePath) throws IOException {
        logger.info("Processing JSON file: {}", filePath);
        // JSON 파일 읽기
        JsonNode rootNode = objectMapper.readTree(new File(filePath));

        // 각 키에 대한 데이터를 데이터베이스에 저장
        if (rootNode.has("growth")) {
            String growthContent = rootNode.get("growth").asText();
            logger.info("Parsed growth content: {}", growthContent);
            Growth growth = Growth.builder().content(growthContent).build();
            growthRepository.save(growth);
            logger.info("Saved growth data: {}", growth);
        }

        if (rootNode.has("meal")) {
            String mealContent = rootNode.get("meal").asText();
            logger.info("Parsed meal content: {}", mealContent);
            Meal meal = Meal.builder().content(mealContent).build();
            mealRepository.save(meal);
            logger.info("Saved meal data: {}", meal);
        }

        if (rootNode.has("poo")) {
            String pooContent = rootNode.get("poo").asText();
            logger.info("Parsed poo content: {}", pooContent);
            Poo poo = Poo.builder().content(pooContent).build();
            pooRepository.save(poo);
            logger.info("Saved poo data: {}", poo);
        }

        if (rootNode.has("sleep")) {
            String sleepContent = rootNode.get("sleep").asText();
            logger.info("Parsed sleep content: {}", sleepContent);
            Sleep sleep = Sleep.builder().content(sleepContent).build();
            sleepRepository.save(sleep);
            logger.info("Saved sleep data: {}", sleep);
        }
    }
}
