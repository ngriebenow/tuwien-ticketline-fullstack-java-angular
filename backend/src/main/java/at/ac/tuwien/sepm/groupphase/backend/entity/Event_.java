package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.Duration;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Event.class)
public class Event_ {

  private static volatile SingularAttribute<Event, Long> id;

  public static volatile SingularAttribute<Event, String> name;

  public static volatile SingularAttribute<Event, EventCategory> category;

  public static volatile SingularAttribute<Event, String> content;

  public static volatile SingularAttribute<Event, Duration> duration;

  public static volatile SingularAttribute<Event, Hall> hall;

  public static volatile SetAttribute<Event, Artist> artists;

}

