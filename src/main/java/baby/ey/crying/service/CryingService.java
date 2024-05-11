package baby.ey.crying.service;

import baby.ey.crying.dto.CryingRequestsDto;
import baby.ey.crying.dto.CryingResponseDto;
import baby.ey.crying.entity.Crying;
import baby.ey.crying.repository.CryingRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class CryingService {
    @Value("${cloud.aws.s3.bucket}")

    private String bucket;
    private final AmazonS3 amazonS3;
    private final CryingRepository cryingRepository;

    public CryingService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Transactional(readOnly = true)
    public List<CryingResponseDto> getCrying() {
        return cryingRepository.findAllByOrderByCreatedDesc().stream()
                .map(CryingResponseDto::new).toList();
    }

    @Transactional
    public CryingResponseDto createCrying(CryingRequestsDto requestDto) {
        Crying crying = new Crying(requestDto);
        cryingRepository.save(crying);
        return new CryingResponseDto(crying);
    }

    public String uploadCrying(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, fileName).toString();
    }

}
