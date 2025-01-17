package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.InvoiceFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

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
   * Return a page of events which satisfy the given filter properties.
   *
   * @param invoiceFilterDto an object containing all the filter criteria.
   * @param pageable the pageable for determining the page.
   * @return a page of invoices.
   */
  List<InvoiceDto> getFiltered(InvoiceFilterDto invoiceFilterDto, Pageable pageable);

  /**
   * Create a new Invoice and Tickets for the specified defined units of one performance for a
   * client.
   *
   * @param reservationRequestDto an object containing information about performance, client and
   *        tickets.
   * @return the paid invoice.
   */
  InvoiceDto buyTickets(@Valid ReservationRequestDto reservationRequestDto);

  /**
   * Issue a new reservation for the specified defined units of one performance for a client.
   *
   * @param reservationRequestDto an object containing information about performance, client and
   *        tickets.
   * @return the unpaid invoice for the reservation.
   */
  InvoiceDto reserveTickets(@Valid ReservationRequestDto reservationRequestDto);

  /**
   * Buy a subset of tickets for an existing invoice.
   *
   * @param id of the invoice to pay tickets for.
   * @param ticketIds list of ticket ids to be paid for.
   * @return the paid invoice.
   */
  InvoiceDto payTickets(Long id, @NotEmpty List<@NotNull Long> ticketIds);
}
