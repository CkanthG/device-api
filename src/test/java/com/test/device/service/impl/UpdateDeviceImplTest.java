package com.test.device.service.impl;

import com.test.device.entity.Device;
import com.test.device.exception.DeviceException;
import com.test.device.mapper.DeviceMapper;
import com.test.device.model.DeviceDto;
import com.test.device.model.DeviceProperties;
import com.test.device.model.State;
import com.test.device.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateDeviceImplTest {

  @InjectMocks
  UpdateDeviceImpl updateDevice;
  @Mock
  DeviceRepository deviceRepository;
  @Mock
  DeviceMapper deviceMapper;

  @Test
  void shouldUpdateDeviceSuccessfully() {

    // given
    Long deviceId = 1L;

    DeviceProperties properties = new DeviceProperties();
    properties.setName("Updated Device");
    properties.setBrand("Apple");

    var localTime = LocalTime.now();

    Device existingDevice = new Device(
      deviceId,
      "Old Device",
      "Old Brand",
      State.AVAILABLE,
      localTime
    );

    Device mappedDevice = new Device(
      deviceId,
      "Updated Device",
      "Apple",
      State.IN_USE,
      localTime
    );

    Device savedDevice = new Device(
      deviceId,
      "Updated Device",
      "Apple",
      State.IN_USE,
      localTime
    );

    DeviceDto expectedDto = new DeviceDto();
    expectedDto.setId(deviceId);
    expectedDto.setName("Updated Device");
    expectedDto.setBrand("Apple");
    expectedDto.setState(State.IN_USE);
    expectedDto.setCreationTime(localTime);

    when(deviceRepository.findById(deviceId))
      .thenReturn(Optional.of(existingDevice));

    when(deviceMapper.toUpdateDevice(properties, existingDevice))
      .thenReturn(mappedDevice);

    when(deviceRepository.save(mappedDevice))
      .thenReturn(savedDevice);

    when(deviceMapper.toDeviceDto(savedDevice))
      .thenReturn(expectedDto);

    // when
    DeviceDto result = updateDevice.updateDevice(deviceId, properties);

    // then
    assertNotNull(result);
    assertEquals(expectedDto, result);

    // verify
    verify(deviceRepository).findById(deviceId);
    verify(deviceMapper).toUpdateDevice(properties, existingDevice);
    verify(deviceRepository).save(mappedDevice);
    verify(deviceMapper).toDeviceDto(savedDevice);
  }

  @Test
  void shouldAllowUpdateWhenDeviceInUseAndRestrictedFieldsNotChanged() {

    // given
    Long deviceId = 1L;

    DeviceProperties properties = new DeviceProperties();
    // brand and name intentionally null

    var localTime = LocalTime.now();

    Device existingDevice = new Device(
      deviceId,
      "Old Device",
      "Old Brand",
      State.IN_USE,
      localTime
    );

    Device updatedDevice = new Device();

    DeviceDto expectedDto = new DeviceDto();

    when(deviceRepository.findById(deviceId))
      .thenReturn(Optional.of(existingDevice));

    when(deviceMapper.toUpdateDevice(properties, existingDevice))
      .thenReturn(updatedDevice);

    when(deviceRepository.save(updatedDevice))
      .thenReturn(updatedDevice);

    when(deviceMapper.toDeviceDto(updatedDevice))
      .thenReturn(expectedDto);

    // when
    DeviceDto result = updateDevice.updateDevice(deviceId, properties);

    // then
    assertNotNull(result);

    // verify
    verify(deviceRepository).findById(deviceId);
    verify(deviceMapper).toUpdateDevice(properties, existingDevice);
    verify(deviceRepository).save(updatedDevice);
    verify(deviceMapper).toDeviceDto(updatedDevice);
  }

  @Test
  void shouldThrowExceptionWhenUpdatingBrandAndDeviceIsInUse() {

    // given
    Long deviceId = 1L;

    DeviceProperties properties = new DeviceProperties();
    properties.setBrand("Samsung");

    var localTime = LocalTime.now();

    Device existingDevice = new Device(
      deviceId,
      "Old Device",
      "Old Brand",
      State.IN_USE,
      localTime
    );

    when(deviceRepository.findById(deviceId))
      .thenReturn(Optional.of(existingDevice));

    // when + then
    DeviceException exception = assertThrows(
      DeviceException.class,
      () -> updateDevice.updateDevice(deviceId, properties)
    );

    assertEquals(
      "Device with id 1 is in use and cannot be updated",
      exception.getMessage()
    );

    // verify
    verify(deviceRepository).findById(deviceId);
  }

  @Test
  void shouldThrowExceptionWhenUpdatingNameAndDeviceIsInUse() {

    // given
    Long deviceId = 1L;

    DeviceProperties properties = new DeviceProperties();
    properties.setName("New Name");

    var localTime = LocalTime.now();

    Device existingDevice = new Device(
      deviceId,
      "Old Device",
      "Old Brand",
      State.IN_USE,
      localTime
    );

    when(deviceRepository.findById(deviceId))
      .thenReturn(Optional.of(existingDevice));

    // when + then
    DeviceException exception = assertThrows(
      DeviceException.class,
      () -> updateDevice.updateDevice(deviceId, properties)
    );

    assertEquals(
      "Device with id 1 is in use and cannot be updated",
      exception.getMessage()
    );

    // verify
    verify(deviceRepository).findById(deviceId);
  }

  @Test
  void shouldThrowExceptionWhenUpdatingBrandAndNameAndDeviceIsInUse() {

    // given
    Long deviceId = 1L;

    DeviceProperties properties = new DeviceProperties();
    properties.setBrand("Samsung");
    properties.setName("Updated");

    var localTime = LocalTime.now();

    Device existingDevice = new Device(
      deviceId,
      "Old Device",
      "Old Brand",
      State.IN_USE,
      localTime
    );

    when(deviceRepository.findById(deviceId))
      .thenReturn(Optional.of(existingDevice));

    // when + then
    DeviceException exception = assertThrows(
      DeviceException.class,
      () -> updateDevice.updateDevice(deviceId, properties)
    );

    assertEquals(
      "Device with id 1 is in use and cannot be updated",
      exception.getMessage()
    );
  }

  @Test
  void shouldThrowExceptionWhenDeviceNotFound() {

    // given
    Long deviceId = 1L;

    DeviceProperties properties = new DeviceProperties();

    when(deviceRepository.findById(deviceId))
      .thenReturn(Optional.empty());

    // when + then
    EntityNotFoundException exception = assertThrows(
      EntityNotFoundException.class,
      () -> updateDevice.updateDevice(deviceId, properties)
    );

    assertEquals(
      "Device with id 1 not found",
      exception.getMessage()
    );

    // verify
    verify(deviceRepository).findById(deviceId);
  }
}