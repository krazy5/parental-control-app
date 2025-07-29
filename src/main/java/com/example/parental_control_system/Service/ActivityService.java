package com.example.parental_control_system.Service;

import com.example.parental_control_system.entity.Activity;
import com.example.parental_control_system.entity.ActivityType;
import com.example.parental_control_system.entity.Child;
import com.example.parental_control_system.entity.Device;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepo;
    private final RuleEngineService ruleEngineService;
    private final NotificationService notificationService;

    public void logActivity(Child child, Device device, ActivityType type,
                            String description, Map<String, Object> metadata) {

        Activity activity = new Activity();
        activity.setChild(child);
        activity.setDevice(device);
        activity.setType(type);
        activity.setDescription(description);
        activity.setTimestamp(LocalDateTime.now());

        if (metadata != null) {
            activity.setMetadata(objectMapper.writeValueAsString(metadata));
        }

        activityRepo.save(activity);

        // Apply rules to check for violations
        ruleEngineService.evaluateActivity(activity);

        log.info("Logged activity for child {}: {} - {}",
                child.getName(), type, description);
    }

    public void logAppUsage(Child child, Device device, String appPackage,
                            int durationMinutes) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("appPackage", appPackage);
        metadata.put("duration", durationMinutes);

        logActivity(child, device, ActivityType.APP_USAGE,
                String.format("Used %s for %d minutes", appPackage, durationMinutes),
                metadata);
    }

    public int getTodayScreenTime(Child child) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        return activityRepo.sumScreenTimeForPeriod(child.getId(), startOfDay, endOfDay);
    }

    public List<ActivitySummaryResponse> getActivitySummary(Child child,
                                                            LocalDate startDate,
                                                            LocalDate endDate) {
        return activityRepo.getActivitySummary(child.getId(),
                startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }
}

