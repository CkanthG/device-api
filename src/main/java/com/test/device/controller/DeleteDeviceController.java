package com.test.device.controller;

import com.test.device.config.DeviceRestController;
import com.test.device.service.DeleteDevice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@DeviceRestController
@RequiredArgsConstructor
@Tag(name = "Delete Device", description = "Delete Device management API")
public class DeleteDeviceController {

  private final DeleteDevice deleteDevice;

  @Operation(summary = "Delete a device")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Device deleted successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request")
  })
  @DeleteMapping("/{deviceId}")
  public ResponseEntity<Void> deleteDevice(@PathVariable("deviceId") Long deviceId) {
    deleteDevice.deleteDevice(deviceId);
    return ResponseEntity.noContent().build();
  }
}
