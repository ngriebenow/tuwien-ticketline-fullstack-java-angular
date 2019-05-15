package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.exception.DataGenerationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("generateData")
public class DataGeneratorComposer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataGeneratorComposer.class);
  private Set<Class<?>> generatedEntities = new HashSet<>();
  private Set<DataGenerator<?>> executedGenerators = new HashSet<>();

  private List<DataGenerator<?>> dataGenerators;

  @Autowired
  public DataGeneratorComposer(List<DataGenerator<?>> dataGenerators) {
    this.dataGenerators = dataGenerators;
  }

  @PostConstruct
  private void generateAllData() throws DataGenerationException {
    if (dataGenerators.isEmpty()) {
      LOGGER.info("No data to generate");
      return;
    }
    for (DataGenerator<?> generator : dataGenerators) {
      delegateDataGeneration(generator);
    }
  }

  private void delegateDataGeneration(DataGenerator<?> generator) throws DataGenerationException {
    if (executedGenerators.contains(generator)) {
      return;
    }

    LOGGER.info(
        "Resolving dependencies for data generator of {} entity",
        generator.getGeneratedType().getSimpleName());

    for (Class<?> dependency : generator.getDependencies()) {
      if (!generatedEntities.contains(dependency)) {
        List<DataGenerator<?>> precedingGenerators =
            dataGenerators.stream()
                .filter(rator -> rator.getGeneratedType().equals(dependency))
                .collect(Collectors.toList());

        if (precedingGenerators.isEmpty()) {
          LOGGER.error("Missing generator for {} entity", dependency.getSimpleName());
          throw new DataGenerationException(
              String.format("Missing generator for %s entity", dependency.getSimpleName()));
        }

        for (DataGenerator<?> precedingGenerator : precedingGenerators) {
          delegateDataGeneration(precedingGenerator);
        }
      }
    }

    LOGGER.info("Generating data for {} entity", generator.getGeneratedType().getSimpleName());
    generator.execute();
    executedGenerators.add(generator);
    generatedEntities.add(generator.getGeneratedType());
  }
}
