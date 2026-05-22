package com.test.device.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.device.model.DeviceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CreateDeviceControllerIT {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void createDevice() throws Exception {

    var response = mockMvc.perform(
        post("/api/devices")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
            {
                "name":"abc",
                "brand":"xyz",
                "state":"AVAILABLE"
            }
          """)
      )
      .andExpect(status().isCreated())
      .andReturn()
      .getResponse();

    var responseDevice = toDeviceDto(response);

    assertThat(responseDevice.getName()).isEqualTo("abc");
    assertThat(responseDevice.getBrand()).isEqualTo("xyz");
    assertThat(responseDevice.getState().name()).isEqualTo("AVAILABLE");
    assertThat(responseDevice.getId()).isNotNull();
    assertThat(responseDevice.getCreationTime()).isNotNull();
  }

  private DeviceDto toDeviceDto(MockHttpServletResponse response)
    throws UnsupportedEncodingException, JsonProcessingException {
    return objectMapper.readValue(
      response.getContentAsString(),
      new TypeReference<>() {}
    );
  }
}