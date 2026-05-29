package com.test.device.service;

import com.test.device.model.DeviceDto;
import com.test.device.model.DeviceResponseDto;
import com.test.device.model.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetAllDevices {
  DeviceResponseDto getAllDevices(String brand, State state, Pageable pageable);
}
