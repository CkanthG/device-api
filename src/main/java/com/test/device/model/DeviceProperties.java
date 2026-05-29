package com.test.device.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceProperties {
  @NotBlank(message = "Name must not be null")
  String name;
  @NotBlank(message = "Brand must not be null")
  String brand;
  @NotNull(message = "State must not be null")
  State state;
}
