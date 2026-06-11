package com.test.device.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceResponseDto {
  List<DeviceDto> content;
  Pageable pageable;
  Long totalElements;
  Long totalPages;
  List<Sort> sort;
  Integer numberOfElements;
  Integer size;
  Integer number;
}
