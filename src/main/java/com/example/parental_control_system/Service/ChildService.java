package com.example.parental_control_system.Service;

import com.example.parental_control_system.dto.ActivityResponse;
import com.example.parental_control_system.dto.ChildMapper;
import com.example.parental_control_system.dto.ChildResponse;
import com.example.parental_control_system.dto.CreateChildRequest;
import com.example.parental_control_system.entity.Child;
import com.example.parental_control_system.entity.User;
import com.example.parental_control_system.repository.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChildService {

    private final ChildRepository childRepo;
    private final UserService   userService;   // fetch current parent

    public List<ChildResponse> getChildrenForCurrentUser() {
        User parent = userService.getCurrentAuthenticatedUser();         // ← Spring Security helper
        return childRepo.findByParentId(parent.getId())
                .stream()
                .map(ChildMapper.INSTANCE::toDto)                // MapStruct
                .toList();
    }

    public ChildResponse createChild(CreateChildRequest req) {
        User parent = userService.getCurrentAuthenticatedUser();
        if (childRepo.countByParentId(parent.getId()) >= 6) {            // business rule
            throw new MaxChildrenReachedException();
        }

        Child entity = ChildMapper.INSTANCE.toEntity(req);
        entity.setParent(parent);
        Child saved = childRepo.save(entity);                            // cascades devices later
        return ChildMapper.INSTANCE.toDto(saved);
    }

    public List<ActivityResponse> getChildActivities(Long childId) {
        Child child = childRepo.findByIdAndParentId(
                        childId,
                        userService.getCurrentAuthenticatedUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Child"));
        return child.getActivities()
                .stream()
                .map(ActivityMapper.INSTANCE::todto)
                .toList();
    }
}


