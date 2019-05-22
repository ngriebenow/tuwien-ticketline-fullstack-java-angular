package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ClientDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.ClientFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import java.util.List;

public interface ClientService {

  /**
   * Find all client entries.
   *
   * @return list of all client entries
   */
  List<ClientDto> findAll(ClientFilterDto client);

  /**
   * Find a single client entry by id.
   *
   * @param id the id of the client entry
   * @return the client entry
   */
  Client findOne(long id);

  /**
   * Store client in database.
   *
   * @param client the client to store
   * @return client read from database.
   */
  ClientDto saveClient(ClientDto client);

  /**
   * Edit client and store new client in database.
   *
   * @param client the client with edited attributes.
   * @return client read from database.
   */
  ClientDto editClient(ClientDto client);

  /**
   * Find a single client entry by id and get dto.
   *
   * @param id the id of the client entry
   * @return the client entry
   */
  ClientDto getOneById(long id);
}
