package com.test.device.service.impl;

import com.test.device.entity.Device;
import com.test.device.exception.DeviceException;
import com.test.device.mapper.DeviceMapper;
import com.test.device.model.DeviceDto;
import com.test.device.model.State;
import com.test.device.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetDeviceImplTest {

  @InjectMocks
  private GetDeviceImpl getDevice;

  @Mock
  private DeviceRepository deviceRepository;
  @Mock
  private DeviceMapper deviceMapper;

  @Test
  void getDeviceById() {

    // given
    var localTime = LocalTime.now();

    Device existingDevice = new Device(
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

    // when
    when(deviceRepository.findById(1L)).thenReturn(Optional.of(existingDevice));
    when(deviceMapper.toDeviceDto(existingDevice)).thenReturn(expectedDto);

    // then
    var result = getDevice.getDeviceById(1L);

    assertThat(result).isEqualTo(expectedDto);

    // verify
    verify(deviceRepository).findById(1L);
    verify(deviceMapper).toDeviceDto(existingDevice);
  }

  @Test
  void getDevice_ById_NotFound() {

    // when
    when(deviceRepository.findById(1L)).thenThrow(new DeviceException("Device not found"));

    // then
    assertThrows(
      DeviceException.class,
      () -> getDevice.getDeviceById(1L)
    );

    // verify
    verify(deviceRepository).findById(1L);
  }
}
