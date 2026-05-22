package com.test.device.mapper;

import com.test.device.entity.Device;
import com.test.device.model.DeviceProperties;
import com.test.device.model.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceMapperTest {

  private DeviceMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new DeviceMapper();
  }

  @Test
  void toDevice() {
    var deviceProperties = new DeviceProperties("Test Device", "Test Brand", State.AVAILABLE);

    var response = mapper.toDevice(deviceProperties);

    assertThat(response.getName()).isEqualTo("Test Device");
    assertThat(response.getBrand()).isEqualTo("Test Brand");
    assertThat(response.getState()).isEqualTo(State.AVAILABLE);
  }

  @Test
  void toDeviceDto() {
    var device = new Device(1L, "Test Device", "Test Brand", State.IN_USE, LocalTime.now());

    var response = mapper.toDeviceDto(device);

    assertThat(response.getId()).isEqualTo(1L);
    assertThat(response.getName()).isEqualTo("Test Device");
    assertThat(response.getBrand()).isEqualTo("Test Brand");
    assertThat(response.getState()).isEqualTo(State.IN_USE);
  }

  @Test
  void toUpdateDeviceDto() {
    var deviceProperties = new DeviceProperties("Updated Device", "Updated Brand", State.AVAILABLE);
    var device = new Device(1L, "Test Device", "Test Brand", State.AVAILABLE, LocalTime.now());

    var response = mapper.toUpdateDevice(deviceProperties, device);

    assertThat(response.getId()).isEqualTo(1L);
    assertThat(response.getName()).isEqualTo("Updated Device");
    assertThat(response.getBrand()).isEqualTo("Updated Brand");
    assertThat(response.getState()).isEqualTo(State.AVAILABLE);
    assertThat(response.getCreationTime()).isEqualTo(device.getCreationTime());
  }

  @Test
  void toUpdatePartialDeviceProperties() {
    var deviceProperties = new DeviceProperties("Updated Device", null, State.AVAILABLE);
    var device = new Device(1L, "Test Device", "Test Brand", State.AVAILABLE, LocalTime.now());

    var response = mapper.toUpdateDevice(deviceProperties, device);

    assertThat(response.getId()).isEqualTo(1L);
    assertThat(response.getName()).isEqualTo("Updated Device");
    assertThat(response.getBrand()).isEqualTo("Test Brand");
    assertThat(response.getState()).isEqualTo(State.AVAILABLE);
    assertThat(response.getCreationTime()).isEqualTo(device.getCreationTime());
  }
}
