package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.DetailedMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.MessageMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/messages")
@Api(value = "messages")
public class MessageEndpoint {

  private final MessageService messageService;
  private final MessageMapper messageMapper;

  public MessageEndpoint(MessageService messageService, MessageMapper messageMapper) {
    this.messageService = messageService;
    this.messageMapper = messageMapper;
  }

  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(
      value = "Get list of simple message entries",
      authorizations = {@Authorization(value = "apiKey")})
  public List<SimpleMessageDto> findAll() {
    return messageMapper.messageToSimpleMessageDto(messageService.findAll());
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation(
      value = "Get detailed information about a specific message entry",
      authorizations = {@Authorization(value = "apiKey")})
  public DetailedMessageDto find(@PathVariable Long id) {
    return messageMapper.messageToDetailedMessageDto(messageService.findOne(id));
  }

  @RequestMapping(method = RequestMethod.POST)
  @PreAuthorize("hasRole('ADMIN')")
  @ApiOperation(
      value = "Publish a new messages entry",
      authorizations = {@Authorization(value = "apiKey")})
  public DetailedMessageDto publishMessage(@RequestBody DetailedMessageDto detailedMessageDto) {
    Message message = messageMapper.detailedMessageDtoToMessage(detailedMessageDto);
    message = messageService.publishMessage(message);
    return messageMapper.messageToDetailedMessageDto(message);
  }
}
