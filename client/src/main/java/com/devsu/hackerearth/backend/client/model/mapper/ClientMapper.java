package com.devsu.hackerearth.backend.client.model.mapper;

import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;

@Component
public class ClientMapper {
    public Client toModel(ClientDto dto) {
        Client model = new Client();
        model.setId(dto.getId());
        model.setActive(dto.isActive());
        model.setAddress(dto.getAddress());
        model.setAge(dto.getAge());
        model.setDni(dto.getDni());
        model.setGender(dto.getGender());
        model.setPassword(dto.getPassword()); // hay que hash???
        model.setName(dto.getName());
        model.setPhone(dto.getPhone());
        return model;
    }

    public ClientDto toDto(Client client) {
        ClientDto dto = new ClientDto();
        dto.setId(client.getId());
        dto.setActive(client.isActive());
        dto.setAddress(client.getAddress());
        dto.setAge(client.getAge());
        dto.setDni(client.getDni());
        dto.setGender(client.getGender());
        dto.setPassword(client.getPassword());
        dto.setName(client.getName());
        dto.setPhone(client.getPhone());
        return dto;
    }
}
