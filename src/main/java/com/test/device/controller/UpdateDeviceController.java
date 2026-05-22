package com.test.device.controller;

import com.test.device.config.DeviceRestController;
import com.test.device.model.DeviceDto;
import com.test.device.model.DeviceProperties;
import com.test.device.service.UpdateDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@DeviceRestController
@RequiredArgsConstructor
public class UpdateDeviceController {

  private final UpdateDevice updateDevice;

  @PutMapping("/{deviceId}")
  public ResponseEntity<DeviceDto> updateDevice(
    @PathVariable Long deviceId,
    @RequestBody DeviceProperties deviceProperties
  ) {
    var updatedDevice = updateDevice.updateDevice(deviceId, deviceProperties);
    return ResponseEntity.ok(updatedDevice);
  }
}
