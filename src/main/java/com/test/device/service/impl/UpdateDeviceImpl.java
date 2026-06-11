package com.test.device.service.impl;

import com.test.device.entity.Device;
import com.test.device.exception.DeviceException;
import com.test.device.mapper.DeviceMapper;
import com.test.device.model.DeviceDto;
import com.test.device.model.DeviceProperties;
import com.test.device.model.State;
import com.test.device.repository.DeviceRepository;
import com.test.device.service.UpdateDevice;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateDeviceImpl implements UpdateDevice {

  private final DeviceMapper deviceMapper;
  private final DeviceRepository deviceRepository;

  @Override
  public DeviceDto updateDevice(Long deviceId, DeviceProperties deviceProperties) {
    var device = getDevice(deviceId);

    preventUpdateWhenInUse(deviceProperties, device);

    var updateDevice = deviceMapper.toUpdateDevice(deviceProperties, device);
    var updatedDevice = deviceRepository.save(updateDevice);

    return deviceMapper.toDeviceDto(updatedDevice);
  }

  private void preventUpdateWhenInUse(DeviceProperties deviceProperties, Device device) {
    if (
      device.getState() == State.IN_USE &&
      (deviceProperties.getBrand() != null || deviceProperties.getName() != null)
    ) {
      throw new DeviceException("Device with id " + device.getId() + " is in use and cannot be updated");
    }
  }

  private Device getDevice(Long deviceId) {
    return deviceRepository.findById(deviceId).orElseThrow(
      () -> new EntityNotFoundException("Device with id " + deviceId + " not found")
    );
  }
}
