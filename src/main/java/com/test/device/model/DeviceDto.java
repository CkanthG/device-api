package com.test.device.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {
  Long id;
  String name;
  String brand;
  State state;
  LocalTime creationTime;
}
