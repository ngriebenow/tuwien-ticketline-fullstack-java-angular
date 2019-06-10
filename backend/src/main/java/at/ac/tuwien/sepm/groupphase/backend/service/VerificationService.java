package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketValidationDto;
import com.google.zxing.common.BitMatrix;

public interface VerificationService {

  /**
   * Check ticket for validity.
   */
  void validateTicket(TicketValidationDto ticket);

  /**
   * Generate QR-Code for ticket.
   *
   * @param id the ticket id
   * @return qr-code
   */
  BitMatrix generateQrCode(long id);
}
