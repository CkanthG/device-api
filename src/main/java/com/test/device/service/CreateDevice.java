package com.test.device.service;

import com.test.device.model.DeviceDto;
import com.test.device.model.DeviceProperties;

public interface CreateDevice {
  DeviceDto createDevice(DeviceProperties deviceProperties);
}
