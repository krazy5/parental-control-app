package com.example.parental_control_system.Service;

import com.example.parental_control_system.Filter.ContentFilter;
import com.example.parental_control_system.entity.Device;
import com.example.parental_control_system.repository.ContentFilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentFilterService {

    private final ContentFilterRepository contentFilterRepo;
    private final ActivityService activityService;

    public boolean isBlocked(String deviceId, String url) {
        Device device = deviceService.findByDeviceId(deviceId);
        List<ContentFilter> filters = contentFilterRepo.findByChildAndIsActive(
                device.getChild(), true);

        for (ContentFilter filter : filters) {
            if (matchesFilter(url, filter)) {
                // Log the activity
                activityService.logActivity(device.getChild(),
                        ActivityType.CONTENT_BLOCKED, url);

                return filter.getAction() == FilterAction.BLOCK;
            }
        }
        return false;
    }

    private boolean matchesFilter(String url, ContentFilter filter) {
        switch (filter.getType()) {
            case WEBSITE:
                return url.contains(filter.getValue());
            case KEYWORD:
                return url.toLowerCase().contains(filter.getValue().toLowerCase());
            case CATEGORY:
                return matchesCategory(url, filter.getValue());
            default:
                return false;
        }
    }

}
