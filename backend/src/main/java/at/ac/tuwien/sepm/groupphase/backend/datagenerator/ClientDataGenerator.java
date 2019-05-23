package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import at.ac.tuwien.sepm.groupphase.backend.repository.ClientRepository;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("generateData")
public class ClientDataGenerator implements DataGenerator<Client> {

  private static final Faker FAKER = new Faker(new Locale("de-at"));
  private static final int MAX_CLIENT_COUNT = 103;
  private static Set<Class<?>> dependencies = new HashSet<>();
  private ClientRepository clientRepository;

  @Autowired
  public ClientDataGenerator(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public void execute() {
    List<Client> generatedClients = new ArrayList<>(MAX_CLIENT_COUNT);
    for (int i = 0; i < MAX_CLIENT_COUNT; i++) {
      String name = FAKER.name().firstName();
      String surName = FAKER.name().lastName();
      String email =
          String.format(
              "%s@%s.%s",
              name.toLowerCase(), surName.toLowerCase(), FAKER.internet().domainSuffix());
      generatedClients.add(new Client.Builder().name(name).surname(surName).email(email).build());
    }
    clientRepository.saveAll(generatedClients);
  }

  @Override
  public Class<Client> getGeneratedType() {
    return Client.class;
  }

  @Override
  public Set<Class<?>> getDependencies() {
    return dependencies;
  }
}
