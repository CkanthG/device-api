package com.test.device.service;

import com.test.device.model.DeviceDto;

public interface GetDevice {
  DeviceDto getDeviceById(Long deviceId);
}
