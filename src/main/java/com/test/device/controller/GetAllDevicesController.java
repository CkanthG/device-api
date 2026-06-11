package com.test.device.controller;

import com.test.device.config.DeviceRestController;
import com.test.device.model.DeviceDto;
import com.test.device.model.DeviceResponseDto;
import com.test.device.model.State;
import com.test.device.service.GetAllDevices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@DeviceRestController
@RequiredArgsConstructor
@Tag(name = "Get All Devices", description = "Get All Devices management API")
public class GetAllDevicesController {

  private final GetAllDevices getAllDevices;

  @Operation(summary = "Get all devices")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Devices retrieved successfully")
  })
  @GetMapping
  public ResponseEntity<DeviceResponseDto> getAllDevices(
    @RequestParam(required = false) String brand,
    @RequestParam(required = false) State state,
    Pageable pageable
  ) {
    return ResponseEntity.ok(getAllDevices.getAllDevices(brand, state, pageable));
  }
}
