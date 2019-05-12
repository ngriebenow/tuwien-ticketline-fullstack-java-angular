package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

}
