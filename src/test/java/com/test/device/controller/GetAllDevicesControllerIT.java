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
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import com.test.device.TestcontainersConfiguration;

import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class GetAllDevicesControllerIT {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private DeviceRepository deviceRepository;

  Device device1, device2, device3;

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
    device3 = new Device(
      null,
      "ghi",
      "rst",
      State.IN_ACTIVE,
      LocalTime.now()
    );

    deviceRepository.saveAll(List.of(device1, device2, device3));
  }

  @AfterEach
  void tearDown() {
    deviceRepository.deleteAll();
  }

  @Test
  void getAllDevices() throws Exception {

    var result = mockMvc.perform(
      get("/api/devices")
    ).andExpect(status().isOk())
      .andReturn()
      .getResponse();

    var response = toDeviceDtoList(result);

    assertThat(response)
      .usingRecursiveFieldByFieldElementComparatorIgnoringFields("creationTime")
      .containsExactlyInAnyOrder(
      new DeviceDto(
        device1.getId(),
        device1.getName(),
        device1.getBrand(),
        device1.getState(),
        device1.getCreationTime()
      ),
      new DeviceDto(
        device2.getId(),
        device2.getName(),
        device2.getBrand(),
        device2.getState(),
        device2.getCreationTime()
      ),
      new DeviceDto(
        device3.getId(),
        device3.getName(),
        device3.getBrand(),
        device3.getState(),
        device3.getCreationTime()
      )
    );
  }

  @Test
  void getDeviceByBrandAndState() throws Exception {
    var result = mockMvc.perform(
      get("/api/devices?brand=xyz&state=AVAILABLE")
    )
      .andExpect(status().isOk())
      .andReturn()
      .getResponse();

    var response = toDeviceDtoList(result);
    assertThat(response.getFirst()).extracting(DeviceDto::getBrand, DeviceDto::getState)
      .containsExactly(
        device1.getBrand(),
        device1.getState()
    );
  }

  @Test
  void getDeviceByState() throws Exception {
    var result = mockMvc.perform(
      get("/api/devices?state=IN_ACTIVE")
    )
      .andExpect(status().isOk())
      .andReturn()
      .getResponse();

    var response = toDeviceDtoList(result);
    assertThat(response.getFirst().getState()).isEqualTo(device3.getState());
  }

  @Test
  void getDeviceByBrand() throws Exception {
    var result = mockMvc.perform(
      get("/api/devices?brand=rst")
    )
      .andExpect(status().isOk())
      .andReturn()
      .getResponse();

    var response = toDeviceDtoList(result);
    assertThat(response.getFirst().getState()).isEqualTo(device3.getState());
  }

  private List<DeviceDto> toDeviceDtoList(MockHttpServletResponse response)
    throws UnsupportedEncodingException, JsonProcessingException {
    return objectMapper.readValue(
      response.getContentAsString(),
      new TypeReference<>() {}
    );
  }
}