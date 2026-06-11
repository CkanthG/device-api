package com.test.device.service.impl;

import com.test.device.entity.Device;
import com.test.device.exception.DeviceException;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteDeviceImplTest {

  @InjectMocks
  private DeleteDeviceImpl deleteDevice;
  @Mock
  private DeviceRepository deviceRepository;

  @Test
  void shouldDeleteDeviceSuccessfully() {

    // given
    Long deviceId = 1L;

    var localTime = LocalTime.now();

    Device device = new Device(
      1L,
      "Device 1",
      "Brand A",
      State.AVAILABLE,
      localTime
    );

    when(deviceRepository.findById(deviceId))
      .thenReturn(Optional.of(device));

    // when
    deleteDevice.deleteDevice(deviceId);

    // verify
    verify(deviceRepository).findById(deviceId);
    verify(deviceRepository).delete(device);
  }

  @Test
  void shouldThrowExceptionWhenDeviceNotFound() {

    // given
    Long deviceId = 1L;

    // when
    when(deviceRepository.findById(deviceId))
      .thenReturn(Optional.empty());

    // then
    EntityNotFoundException exception = assertThrows(
      EntityNotFoundException.class,
      () -> deleteDevice.deleteDevice(deviceId)
    );

    assertEquals(
      "Device with ID 1 not found.",
      exception.getMessage()
    );

    // verify
    verify(deviceRepository).findById(deviceId);
  }

  @Test
  void shouldThrowExceptionWhenDeviceIsInUse() {

    // given
    Long deviceId = 1L;

    var localTime = LocalTime.now();

    Device device = new Device(
      1L,
      "Device 1",
      "Brand A",
      State.IN_USE,
      localTime
    );

    // when
    when(deviceRepository.findById(deviceId))
      .thenReturn(Optional.of(device));

    // then
    DeviceException exception = assertThrows(
      DeviceException.class,
      () -> deleteDevice.deleteDevice(deviceId)
    );

    assertEquals(
      "Cannot delete device with ID 1 because it is currently in use.",
      exception.getMessage()
    );

    // verify
    verify(deviceRepository).findById(deviceId);
  }
}