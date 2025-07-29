package com.example.parental_control_system.Service;

import com.example.parental_control_system.entity.Activity;

// Rule interface
public interface ParentalControlRule {
    boolean appliesTo(Activity activity);
    boolean isViolated(Activity activity);
    String getRuleType();
    String getViolationMessage(Activity activity);
    void executeAction(Activity activity);
}
