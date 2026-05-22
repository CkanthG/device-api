package com.test.device.controller;

import com.test.device.config.DeviceRestController;
import com.test.device.model.DeviceDto;
import com.test.device.model.State;
import com.test.device.service.GetAllDevices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@DeviceRestController
@RequiredArgsConstructor
public class GetAllDevicesController {

  private final GetAllDevices getAllDevices;

  @GetMapping
  public ResponseEntity<List<DeviceDto>> getAllDevices(
    @RequestParam(required = false) String brand,
    @RequestParam(required = false) State state
  ) {
    return ResponseEntity.ok(getAllDevices.getAllDevices(brand, state));
  }
}
