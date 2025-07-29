package com.example.parental_control_system.Service;

import com.example.parental_control_system.entity.Child;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepo;

    public void sendParentNotification(Long parentId, NotificationType type,
                                       String message, Map<String, Object> data) {

        ParentNotification notification = new ParentNotification();
        notification.setParentId(parentId);
        notification.setType(type);
        notification.setMessage(message);
        notification.setData(data);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRead(false);

        notificationRepo.save(notification);

        // Send real-time notification
        messagingTemplate.convertAndSendToUser(
                parentId.toString(),
                "/topic/notifications",
                notification
        );

        log.info("Sent notification to parent {}: {}", parentId, message);
    }

    public void sendRuleViolationAlert(Child child, String ruleType, String details) {
        Map<String, Object> data = new HashMap<>();
        data.put("childId", child.getId());
        data.put("childName", child.getName());
        data.put("ruleType", ruleType);
        data.put("details", details);

        sendParentNotification(
                child.getParent().getId(),
                NotificationType.RULE_VIOLATION,
                String.format("%s violated %s rule", child.getName(), ruleType),
                data
        );
    }

    public void sendScreenTimeLimitAlert(Child child, int usedMinutes, int limitMinutes) {
        Map<String, Object> data = new HashMap<>();
        data.put("childId", child.getId());
        data.put("usedMinutes", usedMinutes);
        data.put("limitMinutes", limitMinutes);

        sendParentNotification(
                child.getParent().getId(),
                NotificationType.SCREEN_TIME_LIMIT,
                String.format("%s has reached screen time limit (%d/%d minutes)",
                        child.getName(), usedMinutes, limitMinutes),
                data
        );
    }
}

