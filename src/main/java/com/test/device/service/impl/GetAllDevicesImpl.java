package com.test.device.service.impl;

import com.test.device.entity.Device;
import com.test.device.mapper.DeviceMapper;
import com.test.device.model.DeviceDto;
import com.test.device.model.DeviceResponseDto;
import com.test.device.model.Sort;
import com.test.device.model.State;
import com.test.device.repository.DeviceRepository;
import com.test.device.service.GetAllDevices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class GetAllDevicesImpl implements GetAllDevices {

  private final DeviceRepository deviceRepository;
  private final DeviceMapper deviceMapper;

  @Override
  public DeviceResponseDto getAllDevices(String brand, State state, Pageable pageable) {
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

    Page<Device> devicePage = deviceRepository.findAll(specification, pageable);

    List<DeviceDto> results = devicePage.getContent()
      .stream()
      .map(deviceMapper::toDeviceDto)
      .toList();

    List<Sort> sort = StreamSupport.stream(pageable.getSort().spliterator(), false)
      .map(s -> new Sort(s.getProperty(), s.getDirection()))
      .toList();

    return new DeviceResponseDto(
      results,
      pageable,
      devicePage.getTotalElements(),
      (long) devicePage.getTotalPages(),
      sort,
      devicePage.getNumberOfElements(),
      devicePage.getSize(),
      devicePage.getNumber()
    );
  }
}
