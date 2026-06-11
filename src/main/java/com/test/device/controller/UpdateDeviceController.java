package com.test.device.controller;

import com.test.device.config.DeviceRestController;
import com.test.device.model.DeviceDto;
import com.test.device.model.DeviceProperties;
import com.test.device.service.UpdateDevice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@DeviceRestController
@RequiredArgsConstructor
@Tag(name = "Update Device", description = "Update Device management API")
public class UpdateDeviceController {

  private final UpdateDevice updateDevice;

  @Operation(summary = "Update a device")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Device updated successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request")
  })
  @PutMapping("/{deviceId}")
  public ResponseEntity<DeviceDto> updateDevice(
    @PathVariable Long deviceId,
    @RequestBody DeviceProperties deviceProperties
  ) {
    var updatedDevice = updateDevice.updateDevice(deviceId, deviceProperties);
    return ResponseEntity.ok(updatedDevice);
  }
}
