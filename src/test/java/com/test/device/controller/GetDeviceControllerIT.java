package com.test.device.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.device.entity.Device;
import com.test.device.model.DeviceDto;
import com.test.device.model.State;
import com.test.device.repository.DeviceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetDeviceControllerIT {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private DeviceRepository deviceRepository;

  Long deviceId;
  Device device;

  @BeforeEach
  void setUp() {
    device = new Device(
      null,
      "abc",
      "xyz",
      State.AVAILABLE,
      LocalTime.now()
    );

    deviceId = deviceRepository.save(device).getId();
  }

  @AfterEach
  void tearDown() {
    deviceRepository.deleteAll();
  }

  @Test
  void getDeviceById() throws Exception {

    var result = mockMvc.perform(
      get("/api/devices/" + deviceId)
      ).andReturn()
      .getResponse();

    var responseDevice = toDeviceDto(result);

    assertThat(responseDevice.getName()).isEqualTo("abc");
    assertThat(responseDevice.getBrand()).isEqualTo("xyz");
    assertThat(responseDevice.getState().name()).isEqualTo("AVAILABLE");
    assertThat(responseDevice.getId()).isNotNull();
    assertThat(responseDevice.getCreationTime()).isNotNull();
  }

  @Test
  void getDeviceByIdNotFound() throws Exception {
    mockMvc.perform(
      get("/api/devices/99999")
    )
      .andExpect(status().isNotFound());
  }

  private DeviceDto toDeviceDto(MockHttpServletResponse response)
    throws UnsupportedEncodingException, JsonProcessingException {
    return objectMapper.readValue(
      response.getContentAsString(),
      new TypeReference<>() {}
    );
  }
}
