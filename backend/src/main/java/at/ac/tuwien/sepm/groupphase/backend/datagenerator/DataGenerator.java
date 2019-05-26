package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import java.util.Set;

/**
 * A Class which generates and persists initial data for an Entity.
 *
 * @param <E> The Entity to create data for.
 */
public interface DataGenerator<E> {

  /**
   * Generate and persist data for Entity E.
   */
  void execute();

  /**
   * Get the Class of the generated Entity.
   *
   * @return The Class of E.
   */
  Class<E> getGeneratedType();

  /**
   * Get a set of entities where at least some entries of each are assumed to be present before
   * execution of this generator.
   *
   * @return A set of Entities.
   */
  Set<Class<?>> getDependencies();
}
