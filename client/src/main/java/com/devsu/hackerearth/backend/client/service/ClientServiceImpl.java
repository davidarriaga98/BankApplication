package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.model.mapper.ClientMapper;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

import com.devsu.hackerearth.backend.client.exceptions.ResourceErrorException;
import com.devsu.hackerearth.backend.client.exceptions.ResourceNotFoundException;

@Service
public class ClientServiceImpl implements ClientService {
	private final ClientRepository clientRepository;
	private final ClientMapper clientMapper;

	public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
		this.clientRepository = clientRepository;
		this.clientMapper = clientMapper;
	}

	@Override
	public List<ClientDto> getAll() {
		// Get all clients
		return clientRepository.findAll()
				.stream()
				.map(clientMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) throws ResourceErrorException {
		// Get clients by id
		return clientRepository
				.findById(id)
				.map(clientMapper::toDto)
				.orElse(null);
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		// Create client
		Client client = clientRepository.save(clientMapper.toModel(clientDto));
		return clientMapper.toDto(client);
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		// Update client
		// Client toUpdateClient
		if (!clientRepository.existsById(clientDto.getId())) {
			return null;
		}

		Client updatedClient = clientRepository.save(clientMapper.toModel(clientDto));
		return clientMapper.toDto(updatedClient);
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) throws ResourceNotFoundException {
		// Partial update account
		Optional<Client> oClient = clientRepository.findById(id);
		if (oClient.isEmpty()) {
			throw new ResourceNotFoundException();
		}

		Client client = oClient.get();
		if (client.isActive() == partialClientDto.isActive()) {
			return clientMapper.toDto(client);
		}

		client.setActive(partialClientDto.isActive());
		return clientMapper.toDto(
				clientRepository.save(client));
	}

	@Override
	public void deleteById(Long id) throws ResourceNotFoundException {
		// Delete client
		if (!clientRepository.existsById(id)) {
			throw new ResourceNotFoundException();
		}

		clientRepository.deleteById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return clientRepository
				.existsById(id);
	}
}
