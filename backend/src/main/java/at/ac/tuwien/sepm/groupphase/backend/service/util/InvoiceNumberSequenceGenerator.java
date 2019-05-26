package at.ac.tuwien.sepm.groupphase.backend.service.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Sequence;
import at.ac.tuwien.sepm.groupphase.backend.repository.SequenceRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InvoiceNumberSequenceGenerator {

  private static final String ID = "seq_invoice_number";
  private static final Long START_VALUE = 1L;

  private SequenceRepository sequenceRepository;

  @Autowired
  public InvoiceNumberSequenceGenerator(SequenceRepository sequenceRepository) {
    this.sequenceRepository = sequenceRepository;
  }

  @PostConstruct
  private void initialize() {
    if (!sequenceRepository.existsById(ID)) {
      sequenceRepository.save(new Sequence.Builder().name(ID).nextValue(START_VALUE).build());
    }
  }

  /**
   * Return the next unassigned value of the sequence and increment it by one.
   */
  @Transactional
  public Long getNext() {
    Sequence sequence = sequenceRepository.getOne(ID);
    Long nextValue = sequence.getNextValue();
    sequence.setNextValue(nextValue + 1);
    sequenceRepository.save(sequence);
    return nextValue;
  }
}
