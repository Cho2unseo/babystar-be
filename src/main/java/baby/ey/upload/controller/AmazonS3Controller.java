package baby.ey.upload.controller;

import baby.ey.upload.service.AwsS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Tag(name = "File", description = "파일 업로드 API")

public class AmazonS3Controller {
    private final AwsS3Service awsS3Service;

    @PostMapping("/uploadFile")
    @Operation(summary = "파일 업로드", description = "한 번에 하나의 파일 업로드 가능")
    public ResponseEntity<String> uploadFile(MultipartFile file, String dirName) {
        String fileName = "";
        if (file != null) {
            try {
                fileName = awsS3Service.upload(file, dirName);
                System.out.println("fileName: " + fileName);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return ResponseEntity.ok(fileName);
    }
}

    /*

    @GetMapping("/downloadFile/{fileName}")
    @Operation(summary = "파일 다운로드", description = "S3의 파일 다운로드")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Resource resource = awsS3Service.download(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
        catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    */

/*
    @DeleteMapping("/deleteFile")
    @Operation(summary = "파일 삭제", description = "하나만 삭제 가능")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        awsS3Service.removeNewFile(fileName);
        return ResponseEntity.ok(fileName);
    }

 */