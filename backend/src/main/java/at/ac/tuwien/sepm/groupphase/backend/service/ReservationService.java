package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InvoiceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ReservationRequestDto;
import javax.validation.Valid;

public interface ReservationService {

  /**
   * Issue a new reservation for the specified defined units of one performance for a client.
   *
   * @param reservationRequest an object containing information about performance, client and
   *     tickets.
   * @return the unpaid invoice for the reservation.
   */
  InvoiceDto reserveTickets(@Valid ReservationRequestDto reservationRequest);
}
