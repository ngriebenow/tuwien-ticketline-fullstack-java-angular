package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.time.Duration;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Event.class)
public abstract class Event_ {

  public static final String DURATION = "duration";
  public static final String ARTISTS = "artists";
  public static final String NAME = "name";
  public static final String HALL = "hall";
  public static final String ID = "id";
  public static final String CATEGORY = "category";
  public static final String CONTENT = "content";
  public static volatile SingularAttribute<Event, Duration> duration;
  public static volatile ListAttribute<Event, Artist> artists;
  public static volatile SingularAttribute<Event, String> name;
  public static volatile SingularAttribute<Event, Hall> hall;
  public static volatile SingularAttribute<Event, Long> id;
  public static volatile SingularAttribute<Event, EventCategory> category;
  public static volatile SingularAttribute<Event, String> content;
}
