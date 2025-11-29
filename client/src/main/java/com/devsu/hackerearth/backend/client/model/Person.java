package com.devsu.hackerearth.backend.client.model;

import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public class Person extends Base {
	private String name;
	private String dni;
	private String gender;
	private int age;
	private String address;
	private String phone;
}
