package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.client;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ClientDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

  Client clientDtoToClient(ClientDto clientDto);

  ClientDto clientToClientDto(Client client);
}
