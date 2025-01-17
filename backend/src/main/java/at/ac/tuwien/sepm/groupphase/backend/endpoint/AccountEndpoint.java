package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.UserFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
@Api(value = "users")
public class AccountEndpoint {

  private AccountService accountService;

  public AccountEndpoint(AccountService accountService) {
    this.accountService = accountService;
  }

  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(
      value = "Store new user in database",
      authorizations = {@Authorization(value = "apiKey")})
  @ResponseStatus(code = HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN')")
  public UserDto saveAccount(@RequestBody UserDto user) {
    return accountService.saveUser(user);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get user by id",
      authorizations = {@Authorization(value = "apiKey")})
  @PreAuthorize("hasRole('ADMIN')")
  public UserDto get(@PathVariable String id) {
    return accountService.getOneById(id);
  }

  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(
      value = "Get filtered users",
      authorizations = {@Authorization(value = "apiKey")})
  @PreAuthorize("hasRole('ADMIN')")
  public List<UserDto> getList(UserFilterDto filter) {
    return accountService.findAll(filter);
  }

  @RequestMapping(method = RequestMethod.PUT)
  @ApiOperation(
      value = "Update user",
      authorizations = {@Authorization(value = "apiKey")})
  @PreAuthorize("hasRole('ADMIN')")
  public UserDto update(@RequestBody UserDto updated) {
    return accountService.editUser(updated);
  }
}
