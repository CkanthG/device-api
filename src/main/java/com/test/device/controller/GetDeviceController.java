package com.test.device.controller;

import com.test.device.config.DeviceRestController;
import com.test.device.model.DeviceDto;
import com.test.device.service.GetDevice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@DeviceRestController
@RequiredArgsConstructor
@Tag(name = "Get Device", description = "Get Device management API")
public class GetDeviceController {

  private final GetDevice getDevice;

  @Operation(summary = "Get a device")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Device retrieved successfully")
  })
  @GetMapping("/{deviceId}")
  public ResponseEntity<DeviceDto> getDevice(@PathVariable Long deviceId) {
    return ResponseEntity.ok(getDevice.getDeviceById(deviceId));
  }
}
