package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.awt.Color;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.hibernate.boot.model.relational.Namespace.Name;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PriceCategory.class)
public abstract class PriceCategory_ {

  public static final String EVENT = "event";
  public static final String COLOR = "color";
  public static final String NAME = "name";
  public static final String ID = "id";
  public static final String PRICEINCENTS = "priceincents";
  public static volatile SingularAttribute<PriceCategory, Long> id;
  public static volatile SingularAttribute<PriceCategory, Event> event;
  public static volatile SingularAttribute<PriceCategory, Color> color;
  public static volatile SingularAttribute<PriceCategory, Name> name;
  public static volatile SingularAttribute<PriceCategory, Integer> priceInCents;
}
