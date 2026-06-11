package com.test.device.service.impl;

import com.test.device.mapper.DeviceMapper;
import com.test.device.model.DeviceDto;
import com.test.device.model.DeviceProperties;
import com.test.device.repository.DeviceRepository;
import com.test.device.service.CreateDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDeviceImpl implements CreateDevice {

  private final DeviceMapper deviceMapper;
  private final DeviceRepository deviceRepository;

  @Override
  public DeviceDto createDevice(DeviceProperties deviceProperties) {
    var device = deviceMapper.toDevice(deviceProperties);
    var savedDevice = deviceRepository.save(device);
    return deviceMapper.toDeviceDto(savedDevice);
  }

}
