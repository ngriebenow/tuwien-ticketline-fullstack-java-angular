package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketValidationDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.InternalServerError;
import at.ac.tuwien.sepm.groupphase.backend.service.VerificationService;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/verification")
@Api(value = "verification")
public class VerificationEndpoint {

  private VerificationService verificationService;

  public VerificationEndpoint(VerificationService verificationService) {
    this.verificationService = verificationService;
  }

  /**
   * Java-Doc.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(
      value = "Generate ticket",
      authorizations = {@Authorization(value = "apiKey")})
  public void getQrCode(HttpServletResponse response, @PathVariable Long id) {
    BitMatrix matrix = verificationService.generateQrCode(id);
    response.setContentType("image/png");
    try {
      MatrixToImageWriter.writeToStream(matrix, "PNG", response.getOutputStream());
    } catch (IOException e) {
      throw new InternalServerError();
    }
  }

  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(
      value = "Verify ticket",
      authorizations = {@Authorization(value = "apiKey")})
  public void verify(@RequestBody TicketValidationDto validation) {
    verificationService.validateTicket(validation);
  }
}
