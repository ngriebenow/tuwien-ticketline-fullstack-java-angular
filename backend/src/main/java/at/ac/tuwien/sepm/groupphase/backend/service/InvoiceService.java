package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import javax.validation.Valid;

public interface InvoiceService {

  /**
   * Get one invoice by its id.
   *
   * @param id of the invoice.
   * @return the Invoice.
   * @throws NotFoundException if no such invoice could be found.
   */
  InvoiceDto getOneById(Long id);

  /**
   * Create a new Invoice and Tickets for the specified defined units of one performance for a
   * client.
   *
   * @param reservationRequestDto an object containing information about performance, client and
   *     tickets.
   * @param isPaid whether the invoice should be flagged as paid right away.
   * @return a new unpaid invoice.
   */
  InvoiceDto createInvoice(@Valid ReservationRequestDto reservationRequestDto, boolean isPaid);
}
