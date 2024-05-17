package baby.ey.user.service;

import baby.ey.user.dto.UserRequestsDto;
import baby.ey.user.dto.UserResponseDto;
import baby.ey.user.entity.User;
import baby.ey.user.repository.UserRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
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
public class UserService {
    private final UserRepository userRepository;
    private final AmazonS3 amazonS3;
    @Value("${BUCKET}")
    private String bucket;

    private String changedImageName(String originName) {
        String random = UUID.randomUUID().toString();
        return "profile/" + random + originName;
    }

    private String uploadImageToS3(MultipartFile image) {
        String originName = image.getOriginalFilename();
        String ext = originName.substring(originName.lastIndexOf("."));
        String changedName = changedImageName(originName);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/" + ext);
        try {
            PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(bucket, changedName, image.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return amazonS3.getUrl(bucket, changedName).toString();
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUser() {
        return userRepository.findAllByOrderByCreatedDesc().stream()
                .map(UserResponseDto::new).toList();
    }

    @Transactional
    public UserResponseDto createUser(MultipartFile image, UserRequestsDto userRequestsDto) {
        String imagePath = uploadImageToS3(image);
        userRequestsDto.setPath(imagePath);
        User user = new User(userRequestsDto);
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto getUser(String email) {
        return userRepository.findByEmail(email).map(UserResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("해당 가입 정보가 없습니다.")
        );
    }

    @Transactional
    public UserResponseDto updateUser(String email, MultipartFile image, UserRequestsDto userRequestsDto) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 가입 정보가 없습니다.")
        );
        String imagePath = uploadImageToS3(image);
        userRequestsDto.setPath(imagePath);
        user.update(userRequestsDto);
        return new UserResponseDto(user);
    }
}