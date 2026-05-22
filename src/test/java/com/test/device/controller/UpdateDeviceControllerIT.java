package com.test.device.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.device.entity.Device;
import com.test.device.model.DeviceDto;
import com.test.device.model.State;
import com.test.device.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UpdateDeviceControllerIT {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  DeviceRepository deviceRepository;

  @Test
  void updateDevice() throws Exception {
    var device = deviceRepository.save(
      new Device(null, "abc", "xyz", State.AVAILABLE, LocalTime.now())
    );

    var response = mockMvc.perform(
        put("/api/devices/" + device.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
            {
                "name":"abc update",
                "brand":"xyz update",
                "state":"AVAILABLE"
            }
          """)
      )
      .andExpect(status().isOk())
      .andReturn()
      .getResponse();

    var updatedDevice = toDeviceDto(response);

    assertThat(updatedDevice.getName()).isEqualTo("abc update");
    assertThat(updatedDevice.getBrand()).isEqualTo("xyz update");
    assertThat(updatedDevice.getState().name()).isEqualTo("AVAILABLE");
    assertThat(updatedDevice.getId()).isNotNull();
    assertThat(updatedDevice.getCreationTime()).isNotNull();
  }

  @Test
  void updateDeviceFailedWhenDeviceIsInUse() throws Exception {
    var device = deviceRepository.save(
      new Device(null, "abc", "xyz", State.IN_USE, LocalTime.now())
    );
    var response = mockMvc.perform(
      put("/api/devices/" + device.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content("""
        {
          "name":"abc update",
          "brand":"xyz update"
        }
      """)
      )
      .andExpect(status().isBadRequest());
  }

  @Test
  void updateDeviceFailedWhenStateIsInvalid() throws Exception {
    var device = deviceRepository.save(
      new Device(null, "abc", "xyz", State.AVAILABLE, LocalTime.now())
    );
    var response = mockMvc.perform(
      put("/api/devices/" + device.getId())
      .contentType(MediaType.APPLICATION_JSON)
      .content("""
        {
          "name":"abc update",
          "brand":"xyz update",
          "state": "INVALID"
        }
      """)
      )
      .andExpect(status().isInternalServerError());
  }

  @Test
  void updateDeviceFailedWhenDeviceNotFound() throws Exception {
    var response = mockMvc.perform(
      put("/api/devices/9999")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
          {
            "name":"abc update",
            "brand":"xyz update"
          }
        """)
    )
      .andExpect(status().isNotFound());
  }

  @Test
  void updatePartialDeviceProperties() throws Exception {
    var device = deviceRepository.save(
      new Device(null, "abc", "xyz", State.IN_USE, LocalTime.now())
    );
    var response = mockMvc.perform(
      put("/api/devices/" + device.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
          {
            "state":"IN_ACTIVE"
          }
        """)
      ).andExpect(status().isOk()).andReturn().getResponse();

    var updatedDevice = toDeviceDto(response);
    assertThat(updatedDevice.getState().name()).isEqualTo("IN_ACTIVE");;
  }

  private DeviceDto toDeviceDto(MockHttpServletResponse response)
    throws UnsupportedEncodingException, JsonProcessingException {
    return objectMapper.readValue(
      response.getContentAsString(),
      new TypeReference<>() {}
    );
  }
}