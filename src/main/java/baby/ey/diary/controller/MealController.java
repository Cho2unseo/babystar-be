package baby.ey.diary.controller;

import baby.ey.diary.dto.MealResponseDto;
import baby.ey.diary.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Meal", description = "식사 키워드 API")
public class MealController {
    private final MealService mealService;

    @GetMapping("/api/meal")
    @Operation(summary = "식사 키워드 조회", description = "식사 키워드 조회 후 리스트 반환 API")
    public List<MealResponseDto> getGrowth() {
        return mealService.getMeal();
    }
}
