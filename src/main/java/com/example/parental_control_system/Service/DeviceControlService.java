package com.example.parental_control_system.Service;

import com.example.parental_control_system.dto.CommandResultMessage;
import com.example.parental_control_system.entity.CommandStatus;
import com.example.parental_control_system.entity.CommandType;
import com.example.parental_control_system.entity.Device;
import com.example.parental_control_system.entity.DeviceCommand;
import com.example.parental_control_system.repository.DeviceCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class DeviceControlService {

    private final DeviceCommandRepository deviceCommandRepo;
    private final NotificationService notificationService;
    private final SimpMessagingTemplate messagingTemplate;

    public void lockDevice(String deviceId, String reason) {
        Device device = deviceService.findByDeviceId(deviceId);

        DeviceCommand command = new DeviceCommand();
        command.setDevice(device);
        command.setType(CommandType.LOCK);
        command.setParameters(String.format("{\"reason\":\"%s\"}", reason));
        command.setStatus(CommandStatus.PENDING);
        command.setCreatedAt(LocalDateTime.now());

        deviceCommandRepo.save(command);

        // Send command to device via WebSocket
        sendCommandToDevice(deviceId, command);

        // Notify parent
        notificationService.sendParentNotification(
                device.getChild().getParent().getId(),
                NotificationType.DEVICE_LOCKED,
                String.format("Device %s has been locked: %s",
                        device.getDeviceName(), reason),
                Map.of("deviceId", deviceId, "reason", reason)
        );
    }

    public void setAppTimeLimit(String deviceId, String appPackage, int limitMinutes) {
        Device device = deviceService.findByDeviceId(deviceId);

        Map<String, Object> params = new HashMap<>();
        params.put("appPackage", appPackage);
        params.put("limitMinutes", limitMinutes);

        DeviceCommand command = new DeviceCommand();
        command.setDevice(device);
        command.setType(CommandType.SET_APP_LIMIT);
        command.setParameters(objectMapper.writeValueAsString(params));
        command.setStatus(CommandStatus.PENDING);

        deviceCommandRepo.save(command);
        sendCommandToDevice(deviceId, command);
    }

    private void sendCommandToDevice(String deviceId, DeviceCommand command) {
        messagingTemplate.convertAndSend(
                "/topic/device/" + deviceId + "/commands",
                command
        );
    }

    @MessageMapping("/device/{deviceId}/command-result")
    public void handleCommandResult(@DestinationVariable String deviceId,
                                    CommandResultMessage result) {
        DeviceCommand command = deviceCommandRepo.findById(result.getCommandId())
                .orElseThrow(() -> new ResourceNotFoundException("Command not found"));

        command.setStatus(result.isSuccess() ?
                CommandStatus.COMPLETED : CommandStatus.FAILED);
        command.setResult(result.getMessage());
        command.setExecutedAt(LocalDateTime.now());

        deviceCommandRepo.save(command);
    }
}

