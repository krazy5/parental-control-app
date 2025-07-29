package com.example.parental_control_system.Service;

import com.example.parental_control_system.entity.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleEngineService {

    private final List<ParentalControlRule> rules;
    private final NotificationService notificationService;

    public void evaluateActivity(Activity activity) {
        rules.stream()
                .filter(rule -> rule.appliesTo(activity))
                .forEach(rule -> {
                    if (rule.isViolated(activity)) {
                        handleRuleViolation(rule, activity);
                    }
                });
    }

    private void handleRuleViolation(ParentalControlRule rule, Activity activity) {
        notificationService.sendRuleViolationAlert(
                activity.getChild(),
                rule.getRuleType(),
                rule.getViolationMessage(activity)
        );

        // Execute rule action (lock device, send warning, etc.)
        rule.executeAction(activity);
    }
}

// Example rule implementation
@Component
public abstract class InappropriateContentRule implements ParentalControlRule {

    private final ContentFilterService contentFilterService;
    private final DeviceControlService deviceControlService;

    @Override
    public boolean appliesTo(Activity activity) {
        return activity.getType() == ActivityType.WEB_VISIT;
    }

    @Override
    public boolean isViolated(Activity activity) {
        return contentFilterService.isInappropriate(activity.getUrl());
    }

    @Override
    public void executeAction(Activity activity) {
        // Block the website and send warning
        contentFilterService.blockWebsite(activity.getUrl());
        deviceControlService.sendWarning(
                activity.getDevice().getDeviceId(),
                "Inappropriate content detected"
        );
    }
}

