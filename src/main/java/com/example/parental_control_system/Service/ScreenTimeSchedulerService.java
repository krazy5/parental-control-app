package com.example.parental_control_system.Service;

import com.example.parental_control_system.entity.Child;
import com.example.parental_control_system.entity.ScreenTimeRule;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenTimeSchedulerService {

    private final ScreenTimeRuleRepository screenTimeRuleRepo;
    private final DeviceControlService deviceControlService;
    private final ActivityService activityService;

    @Scheduled(fixedRate = 60000) // Check every minute
    public void enforceScreenTimeRules() {
        List<ScreenTimeRule> activeRules = screenTimeRuleRepo.findByIsActive(true);

        for (ScreenTimeRule rule : activeRules) {
            LocalTime now = LocalTime.now();
            DayOfWeek today = LocalDate.now().getDayOfWeek();

            // Check if current time is outside allowed hours
            if (isOutsideAllowedHours(rule, now, today)) {
                lockChildDevices(rule.getChild(), "Outside allowed hours");
            }

            // Check daily limit
            int todayUsage = activityService.getTodayScreenTime(rule.getChild());
            if (todayUsage >= rule.getDailyLimitMinutes()) {
                lockChildDevices(rule.getChild(), "Daily limit exceeded");
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // Reset daily at midnight
    public void resetDailyScreenTime() {
        // Reset daily usage counters
        activityService.resetDailyCounters();
    }

    private void lockChildDevices(Child child, String reason) {
        child.getDevices().forEach(device -> {
            deviceControlService.lockDevice(device.getDeviceId(), reason);
        });
    }
}

