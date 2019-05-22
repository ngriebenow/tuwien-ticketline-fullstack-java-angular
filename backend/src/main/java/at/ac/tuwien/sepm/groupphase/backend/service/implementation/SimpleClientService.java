package at.ac.tuwien.sepm.groupphase.backend.service.implementation;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ClientDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.filter.ClientFilterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Client;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.client.ClientMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.InvalidInputException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ClientRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ClientService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleClientService implements ClientService {

  private ClientRepository clientRepository;
  private ClientMapper clientMapper;

  public SimpleClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
    this.clientRepository = clientRepository;
    this.clientMapper = clientMapper;
  }

  /**
   * Javadoc.
   */
  private static Specification<Client> likeClient(ClientFilterDto clientFilter) {
    return new Specification<Client>() {
      @Override
      public Predicate toPredicate(
          Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> expressions = new ArrayList<>();

        if (clientFilter.getName() != null) {
          Predicate name =
              criteriaBuilder.like(root.get("name"), "%" + clientFilter.getName() + "%");
          expressions.add(name);
        }

        if (clientFilter.getSurname() != null) {
          Predicate surname =
              criteriaBuilder.like(root.get("surname"), "%" + clientFilter.getSurname() + "%");
          expressions.add(surname);
        }

        if (clientFilter.getEmail() != null) {
          Predicate email =
              criteriaBuilder.like(root.get("email"), "%" + clientFilter.getEmail() + "%");
          expressions.add(email);
        }

        Predicate[] predicates = expressions.toArray(new Predicate[expressions.size()]);

        return criteriaBuilder.and(predicates);
      }
    };
  }

  @Transactional(readOnly = true)
  @Override
  public List<ClientDto> findAll(ClientFilterDto client) {
    if (client.getCount() == null) {
      client.setCount(10);
    }
    if (client.getPage() == null) {
      client.setPage(0);
    }
    if (client.getCount() < 0) {
      throw new InvalidInputException("Count must be >0");
    }
    if (client.getPage() < 0) {
      throw new InvalidInputException("Page must be >0");
    }
    Pageable pageable = PageRequest.of(client.getPage(), client.getCount());
    Specification<Client> clientSpecification = likeClient(client);
    Page<Client> clients = clientRepository.findAll(clientSpecification, pageable);
    ArrayList<ClientDto> toret = new ArrayList<>();
    clients.forEach(e -> toret.add(clientMapper.clientToClientDto(e)));
    return toret;
  }

  @Transactional(readOnly = true)
  @Override
  public Client findOne(long id) {
    Client c = clientRepository.getOne(id);
    if (c == null) {
      throw new NotFoundException("Client " + id + " not found!");
    }
    return c;
  }

  @Transactional
  @Override
  public ClientDto saveClient(ClientDto client) {
    Client tmp = clientRepository.saveAndFlush(clientMapper.clientDtoToClient(client));
    return clientMapper.clientToClientDto(tmp);
  }

  private Client updateUserHelper(Client old, ClientDto updated) {
    if (updated.getName() != null) {
      old.setName(updated.getName());
    }
    if (updated.getSurname() != null) {
      old.setSurname(updated.getSurname());
    }
    if (updated.getEmail() != null) {
      old.setEmail(updated.getEmail());
    }
    return old;
  }

  @Transactional
  @Override
  public ClientDto editClient(ClientDto client) {
    if (client.getId() == null) {
      throw new InvalidInputException("Id must be set!");
    }
    Client old = findOne(client.getId());
    old = updateUserHelper(old, client);
    clientRepository.saveAndFlush(old);
    return getOneById(client.getId());
  }

  @Transactional(readOnly = true)
  @Override
  public ClientDto getOneById(long id) {
    return clientMapper.clientToClientDto(findOne(id));
  }
}
