package com.test.device.service.impl;

import com.test.device.entity.Device;
import com.test.device.mapper.DeviceMapper;
import com.test.device.model.DeviceDto;
import com.test.device.model.DeviceProperties;
import com.test.device.model.State;
import com.test.device.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateDeviceImplTest {

  @InjectMocks
  private CreateDeviceImpl createDevice;

  @Mock
  private DeviceRepository deviceRepository;
  @Mock
  private DeviceMapper deviceMapper;

  @Test
  void createDevice() {
    // given
    DeviceProperties properties = new DeviceProperties(
      "Device 1",
      "Type A",
      State.AVAILABLE
    );
    var localTime = LocalTime.now();

    Device mappedDevice = new Device(
      null,
      "Device 1",
      "Brand A",
      State.AVAILABLE,
      localTime
    );

    Device savedDevice = new Device(
      1L,
      "Device 1",
      "Brand A",
      State.AVAILABLE,
      localTime
    );

    DeviceDto expectedDto = new DeviceDto(
      1L,
      "Device 1",
      "Brand A",
      State.AVAILABLE,
      localTime
    );

    when(deviceMapper.toDevice(properties))
      .thenReturn(mappedDevice);

    when(deviceRepository.save(mappedDevice))
      .thenReturn(savedDevice);

    when(deviceMapper.toDeviceDto(savedDevice))
      .thenReturn(expectedDto);

    // when
    DeviceDto result = createDevice.createDevice(properties);

    // then
    assertEquals(expectedDto, result);

    // verify
    verify(deviceMapper).toDevice(properties);
    verify(deviceRepository).save(mappedDevice);
    verify(deviceMapper).toDeviceDto(savedDevice);
  }

}
