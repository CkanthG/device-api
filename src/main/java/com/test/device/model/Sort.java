package com.test.device.model;

import static org.springframework.data.domain.Sort.Direction;

public record Sort(
  String sortBy,
  Direction direction
) {
}
