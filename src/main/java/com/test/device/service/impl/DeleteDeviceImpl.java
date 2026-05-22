package com.test.device.service.impl;

import com.test.device.entity.Device;
import com.test.device.exception.DeviceException;
import com.test.device.model.State;
import com.test.device.repository.DeviceRepository;
import com.test.device.service.DeleteDevice;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteDeviceImpl implements DeleteDevice {

  private final DeviceRepository deviceRepository;

  @Override
  public void deleteDevice(Long deviceId) {

    Device device = getDevice(deviceId);

    validateDeviceCanBeDeleted(device);

    deviceRepository.delete(device);
  }

  private void validateDeviceCanBeDeleted(Device device) {

    if (device.getState() == State.IN_USE) {
      throw new DeviceException("Cannot delete device with ID " + device.getId() + " because it is currently in use.");
    }
  }

  private Device getDevice(Long deviceId) {

    return deviceRepository.findById(deviceId)
      .orElseThrow(
        () -> new EntityNotFoundException("Device with ID " + deviceId + " not found.")
      );
  }
}
