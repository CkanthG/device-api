package com.test.device.controller;

import com.test.device.config.DeviceRestController;
import com.test.device.model.DeviceDto;
import com.test.device.model.DeviceProperties;
import com.test.device.service.CreateDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@DeviceRestController
@RequiredArgsConstructor
public class CreateDeviceController {

  private final CreateDevice createDevice;

  @PostMapping
  public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceProperties deviceProperties) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(createDevice.createDevice(deviceProperties));
  }

}