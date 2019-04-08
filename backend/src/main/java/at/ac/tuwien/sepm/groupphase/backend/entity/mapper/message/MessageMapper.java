package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.DetailedMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MessageSummaryMapper.class)
public interface MessageMapper {

  Message detailedMessageDtoToMessage(DetailedMessageDto detailedMessageDto);

  DetailedMessageDto messageToDetailedMessageDto(Message one);

  List<SimpleMessageDto> messageToSimpleMessageDto(List<Message> all);

  @Mapping(
      source = "text",
      target = "summary",
      qualifiedBy = MessageSummaryMapper.MessageSummary.class)
  SimpleMessageDto messageToSimpleMessageDto(Message one);
}
