package com.test.device.service.impl;

import com.test.device.entity.Device;
import com.test.device.mapper.DeviceMapper;
import com.test.device.model.DeviceDto;
import com.test.device.model.State;
import com.test.device.repository.DeviceRepository;
import com.test.device.service.GetAllDevices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllDevicesImpl implements GetAllDevices {

  private final DeviceRepository deviceRepository;
  private final DeviceMapper deviceMapper;

  @Override
  public List<DeviceDto> getAllDevices(String brand, State state) {
    Specification<Device> specification = (root, query, cb) -> cb.conjunction();

    if (brand != null) {
      specification = specification.and(
        (root, query, cb) ->
          cb.equal(root.get("brand"), brand)
      );
    }

    if (state != null) {
      specification = specification.and(
        (root, query, cb) ->
          cb.equal(root.get("state"), state)
      );
    }

    return deviceRepository.findAll(specification)
      .stream()
      .map(deviceMapper::toDeviceDto)
      .toList();
  }
}
