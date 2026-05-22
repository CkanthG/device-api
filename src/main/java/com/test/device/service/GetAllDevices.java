package com.test.device.service;

import com.test.device.model.DeviceDto;
import com.test.device.model.State;

import java.util.List;

public interface GetAllDevices {
  List<DeviceDto> getAllDevices(String brand, State state);
}
