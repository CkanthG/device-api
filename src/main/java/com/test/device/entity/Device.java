package com.test.device.entity;

import com.test.device.model.State;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "devices")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;

  String name;

  String brand;

  @Enumerated(EnumType.STRING)
  State state;

  LocalTime creationTime;
}
