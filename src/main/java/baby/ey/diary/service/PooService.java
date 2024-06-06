package baby.ey.diary.service;

import baby.ey.diary.dto.PooResponseDto;
import baby.ey.diary.repository.PooRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PooService {
    private final PooRepository pooRepository;

    @Transactional(readOnly = true)
    public List<PooResponseDto> getPoo() {
        return pooRepository.findAllByOrderByCreatedDesc().stream()
                .map(PooResponseDto::new).toList();
    }

}
