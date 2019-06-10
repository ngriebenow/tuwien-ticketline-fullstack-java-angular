package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketValidationDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.exception.InternalServerError;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.VerificationService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleVerificationService implements VerificationService {
  @Autowired private TicketRepository ticketRepository;

  @Override
  public void validateTicket(TicketValidationDto ticket) {
    Ticket t = ticketRepository.getOne(ticket.getId());
    if (t == null) {
      throw new NotFoundException("Could not find ticket with id " + ticket.getId());
    }
    String dbhash = Base64.getEncoder().encodeToString(generateHash(ticket.getId()));
    if (!ticket.getHash().equals(dbhash)) {
      throw new ValidationException("Ticket not valid!");
    }
  }

  @Override
  public BitMatrix generateQrCode(long id) {
    Ticket t = ticketRepository.getOne(id);
    if (t == null) {
      throw new NotFoundException("Could not find ticket with id " + id);
    }
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    byte[] tmp = generateHash(id);
    BitMatrix bitMatrix = null;
    try {
      bitMatrix =
          qrCodeWriter.encode(
              Base64.getEncoder().encodeToString(tmp), BarcodeFormat.QR_CODE, 350, 350);
    } catch (WriterException e) {
      throw new InternalServerError();
    }
    return bitMatrix;
  }

  private byte[] generateHash(long id) {
    Ticket t = ticketRepository.getOne(id);
    MessageDigest digest = null;
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    byte[] salt = Base64.getDecoder().decode(t.getSalt());
    byte[] idAsBytes = longToBytes(id);
    byte[] combined = ArrayUtils.addAll(idAsBytes, salt);
    return digest.digest(combined);
  }

  private byte[] longToBytes(long x) {
    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
    buffer.putLong(x);
    return buffer.array();
  }
}
