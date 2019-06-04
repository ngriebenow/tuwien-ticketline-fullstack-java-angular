package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.LocalDateTime;
import javax.persistence.metamodel.SingularAttribute;
import org.hibernate.boot.model.relational.Namespace.Name;

public class Performance_ {

  public static final String EVENT = "event";
  public static final String NAME = "name";
  public static final String ID = "id";
  public static final String STARTAT = "startAtLocalAndUtc";
  public static volatile SingularAttribute<Performance, Long> id;
  public static volatile SingularAttribute<Performance, Event> event;
  public static volatile SingularAttribute<Performance, Name> name;
  public static volatile SingularAttribute<Performance, LocalDateTime> startAt;
}
