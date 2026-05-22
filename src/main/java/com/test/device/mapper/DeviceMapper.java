package com.test.device.mapper;

import com.test.device.entity.Device;
import com.test.device.model.DeviceDto;
import com.test.device.model.DeviceProperties;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class DeviceMapper {

  public Device toDevice(DeviceProperties deviceProperties) {
    return new Device(
      null,
      deviceProperties.getName(),
      deviceProperties.getBrand(),
      deviceProperties.getState(),
      LocalTime.now()
    );
  }

  public DeviceDto toDeviceDto(Device device) {
    return new DeviceDto(
      device.getId(),
      device.getName(),
      device.getBrand(),
      device.getState(),
      device.getCreationTime()
    );
  }
}
