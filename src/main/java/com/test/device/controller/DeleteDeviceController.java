package com.test.device.controller;

import com.test.device.config.DeviceRestController;
import com.test.device.service.DeleteDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@DeviceRestController
@RequiredArgsConstructor
public class DeleteDeviceController {

  private final DeleteDevice deleteDevice;

  @DeleteMapping("/{deviceId}")
  public ResponseEntity<Void> deleteDevice(@PathVariable("deviceId") Long deviceId) {
    deleteDevice.deleteDevice(deviceId);
    return ResponseEntity.noContent().build();
  }
}
