package baby.ey.diary.controller;

import baby.ey.diary.dto.DiaryRequestsDto;
import baby.ey.diary.dto.DiaryResponseDto;
import baby.ey.diary.dto.SuccessResponseDto;
import baby.ey.diary.service.DiaryService;
import baby.ey.upload.service.AwsS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Diary", description = "육아일기 API")
public class DiaryController {
    private final DiaryService diaryService;
    private final AwsS3Service awsS3Service;

    @GetMapping("/api/diary")
    @Operation(summary = "육아일기 조회", description = "육아일기 조회 후 리스트 반환 API")
    public List<DiaryResponseDto> getDiary() {
        return diaryService.getDiary();
    }

    @PostMapping("/api/post")
    @Operation(summary = "육아일기 작성", description = "육아일기 작성 API")
    public DiaryResponseDto createDiary(@RequestPart(value = "image", required = false) MultipartFile image, @Valid @RequestPart(value = "requestDto") DiaryRequestsDto requestDto) {
        return diaryService.createDiary(image, requestDto);
    }

    /*
    @PostMapping("/api/post/image")
    @Operation(summary = "육아일기 이미지 업로드", description = "육아일기 이미지 업로드 API")
    public String uploadImage(@RequestParam("image") MultipartFile image) {
        return diaryService.uploadImageToS3(image);
    }

     */

    @GetMapping("/api/diary/{id}")
    @Operation(summary = "육아일기 상세 조회", description = "육아일기 상세 조회 API")
    public DiaryResponseDto getDiary(@PathVariable Long id, @RequestBody DiaryRequestsDto diaryRequestsDto, MultipartFile file) throws Exception {
        // String imgPath = awsS3Service.upload(file, "diary");
        return diaryService.getDiary(id);
    }

    @PutMapping("/api/diary/{id}")
    @Operation(summary = "육아일기 수정", description = "육아일기 내용 및 수정일 변경 API")
    public DiaryResponseDto updateDiary(@PathVariable Long id, @RequestBody DiaryRequestsDto requestDto) throws Exception {
        return diaryService.updateDiary(id, requestDto);
    }

    @DeleteMapping("/api/diary/{id}")
    @Operation(summary = "육아일기 삭제", description = "선택한 육아일기 삭제 API")
    public SuccessResponseDto deleteDiary(@PathVariable Long id, @RequestBody DiaryRequestsDto requestDto) throws Exception {
        return diaryService.deleteDiary(id, requestDto);
    }


}
