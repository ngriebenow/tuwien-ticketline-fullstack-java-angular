package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.client;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ClientDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

  ClientDto clientToClientDto(Client client);
}
