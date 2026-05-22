package com.test.device.controller;

import com.test.device.config.DeviceRestController;
import com.test.device.model.DeviceDto;
import com.test.device.model.DeviceProperties;
import com.test.device.service.CreateDevice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@DeviceRestController
@RequiredArgsConstructor
@Tag(name = "Create Device", description = "Create Device management API")
public class CreateDeviceController {

  private final CreateDevice createDevice;

  @Operation(summary = "Create a device")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Device created successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request")
  })
  @PostMapping
  public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceProperties deviceProperties) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(createDevice.createDevice(deviceProperties));
  }

}
