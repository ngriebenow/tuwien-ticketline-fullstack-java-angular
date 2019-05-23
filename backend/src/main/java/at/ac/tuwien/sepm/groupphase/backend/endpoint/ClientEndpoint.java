package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ClientDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.ClientFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clients")
@Api(value = "clients")
public class ClientEndpoint {

  private ClientService clientService;

  @Autowired
  public ClientEndpoint(ClientService clientService) {
    this.clientService = clientService;
  }

  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(
      value = "Store new client in database",
      authorizations = {@Authorization(value = "apiKey")})
  @ResponseStatus(code = HttpStatus.CREATED)
  public ClientDto saveClient(@RequestBody ClientDto client) {
    return clientService.saveClient(client);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get client by id",
      authorizations = {@Authorization(value = "apiKey")})
  public ClientDto get(@PathVariable long id) {
    return clientService.getOneById(id);
  }

  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(
      value = "Get filtered clients",
      authorizations = {@Authorization(value = "apiKey")})
  public List<ClientDto> getList(ClientFilterDto filter) {
    return clientService.findAll(filter);
  }

  @RequestMapping(method = RequestMethod.PUT)
  @ApiOperation(
      value = "Update client",
      authorizations = {@Authorization(value = "apiKey")})
  public ClientDto update(@RequestBody ClientDto updated) {
    return clientService.editClient(updated);
  }
}
