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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JsonToDatabaseService {

    private final GrowthRepository growthRepository;
    private final MealRepository mealRepository;
    private final PooRepository pooRepository;
    private final SleepRepository sleepRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void saveJsonToDatabase(String filePath) throws IOException {
        // JSON 파일 읽기
        JsonNode rootNode = objectMapper.readTree(new File(filePath));

        // 각 키에 대한 데이터를 데이터베이스에 저장
        if (rootNode.has("growth")) {
            Growth growth = Growth.builder().content(rootNode.get("growth").toString()).build();
            growthRepository.save(growth);
        }

        if (rootNode.has("meal")) {
            Meal meal = Meal.builder().content(rootNode.get("meal").toString()).build();
            mealRepository.save(meal);
        }

        if (rootNode.has("poo")) {
            Poo poo = Poo.builder().content(rootNode.get("poo").toString()).build();
            pooRepository.save(poo);
        }

        if (rootNode.has("sleep")) {
            Sleep sleep = Sleep.builder().content(rootNode.get("sleep").toString()).build();
            sleepRepository.save(sleep);
        }
    }
}
