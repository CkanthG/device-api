package com.test.device.controller;

import com.test.device.TestcontainersConfiguration;
import com.test.device.entity.Device;
import com.test.device.model.State;
import com.test.device.repository.DeviceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class DeleteDeviceControllerIT {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private DeviceRepository deviceRepository;

  Device device1, device2;

  @BeforeEach
  void setUp() {
    device1 = new Device(
      null,
      "abc",
      "xyz",
      State.AVAILABLE,
      LocalTime.now()
    );
    device2 = new Device(
      null,
      "def",
      "uvw",
      State.IN_USE,
      LocalTime.now()
    );
    deviceRepository.saveAll(List.of(device1, device2));
  }

  @AfterEach
  void tearDown() {
    deviceRepository.deleteAll();
  }

  @Test
  void deleteDevice() throws Exception {
    mockMvc.perform(
      delete("/api/devices/" + device1.getId())
    ).andExpect(status().isNoContent());
  }

  @Test
  void deleteDeviceFailedWhenInUse() throws Exception {
    mockMvc.perform(
      delete("/api/devices/" + device2.getId())
    ).andExpect(status().isBadRequest());
  }
}
