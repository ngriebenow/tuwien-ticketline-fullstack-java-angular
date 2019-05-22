package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.awt.Color;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.metamodel.SingularAttribute;
import org.hibernate.boot.model.relational.Namespace.Name;

public class Performance_ {

  public static volatile SingularAttribute<Performance, Long> id;
  public static volatile SingularAttribute<Performance, Event> event;
  public static volatile SingularAttribute<Performance, Name> name;
  public static volatile SingularAttribute<Performance, LocalDateTime> startAt;

  public static final String EVENT = "event";
  public static final String NAME = "name";
  public static final String ID = "id";
  public static final String STARTAT = "startAt";
  
}
