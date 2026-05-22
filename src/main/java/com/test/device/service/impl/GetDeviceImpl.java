package com.test.device.service.impl;

import com.test.device.entity.Device;
import com.test.device.mapper.DeviceMapper;
import com.test.device.model.DeviceDto;
import com.test.device.repository.DeviceRepository;
import com.test.device.service.GetDevice;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDeviceImpl implements GetDevice {

  private final DeviceRepository deviceRepository;
  private final DeviceMapper deviceMapper;

  @Override
  public DeviceDto getDeviceById(Long deviceId) {
    var device = getDevice(deviceId);
    return deviceMapper.toDeviceDto(device);
  }

  private Device getDevice(Long deviceId) {
    return deviceRepository.findById(deviceId).orElseThrow(
      () -> new EntityNotFoundException("Device with id " + deviceId + " not found")
    );
  }
}
