package com.test.device.controller;

import com.test.device.config.DeviceRestController;
import com.test.device.model.DeviceDto;
import com.test.device.service.GetDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@DeviceRestController
@RequiredArgsConstructor
public class GetDeviceController {

  private final GetDevice getDevice;

  @GetMapping("/{deviceId}")
  public ResponseEntity<DeviceDto> getDevice(@PathVariable Long deviceId) {
    return ResponseEntity.ok(getDevice.getDeviceById(deviceId));
  }
}
