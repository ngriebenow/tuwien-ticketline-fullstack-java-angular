package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.ZonedDateTime;
import javax.persistence.metamodel.SingularAttribute;
import org.hibernate.boot.model.relational.Namespace.Name;

public class Performance_ {

  public static final String EVENT = "event";
  public static final String NAME = "name";
  public static final String ID = "id";
  public static final String STARTAT = "startAt";
  public static volatile SingularAttribute<Performance, Long> id;
  public static volatile SingularAttribute<Performance, Event> event;
  public static volatile SingularAttribute<Performance, Name> name;
  public static volatile SingularAttribute<Performance, ZonedDateTime> startAt;
}
