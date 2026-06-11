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

  public Device toUpdateDevice(DeviceProperties deviceProperties, Device deviceObject) {
    return new Device(
      deviceObject.getId(),
      deviceProperties.getName() != null ? deviceProperties.getName() : deviceObject.getName(),
      deviceProperties.getBrand() != null ? deviceProperties.getBrand() : deviceObject.getBrand(),
      deviceProperties.getState() != null ? deviceProperties.getState() : deviceObject.getState(),
      deviceObject.getCreationTime()
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
