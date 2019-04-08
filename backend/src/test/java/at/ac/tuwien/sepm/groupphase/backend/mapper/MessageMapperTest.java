package at.ac.tuwien.sepm.groupphase.backend.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.DetailedMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message.MessageMapper;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class MessageMapperTest {

  private static final long NEWS_ID = 1L;
  private static final String NEWS_TITLE = "Headline";
  private static final String NEWS_TEXT =
      "This is a very long text containing all the contents of the message"
          + " and a lot of other more or less useful information.";
  private static final String NEWS_SUMMARY = "This is a very long text containing all the";
  private static final LocalDateTime NEWS_PUBLISHED_AT = LocalDateTime.of(2016, 1, 1, 12, 0, 0, 0);

  @Autowired
  @SuppressWarnings("SpringJavaAutowiredMembersInspection")
  // Suppress warning cause inspection does not know that the cdi annotations are added in the code
  // generation step
  private MessageMapper messageMapper;

  @Test
  public void shouldMapMessageToSimpleMessageDTOShorteningTextToSummary() {
    Message message =
        Message.builder()
            .id(NEWS_ID)
            .publishedAt(NEWS_PUBLISHED_AT)
            .title(NEWS_TITLE)
            .text(NEWS_TEXT)
            .build();
    SimpleMessageDto simpleMessageDTO = messageMapper.messageToSimpleMessageDto(message);
    assertThat(simpleMessageDTO).isNotNull();
    assertThat(simpleMessageDTO.getId()).isEqualTo(1L);
    assertThat(simpleMessageDTO.getPublishedAt()).isEqualTo(NEWS_PUBLISHED_AT);
    assertThat(simpleMessageDTO.getTitle()).isEqualTo(NEWS_TITLE);
    assertThat(simpleMessageDTO.getSummary()).isEqualTo(NEWS_SUMMARY);
  }

  @Test
  public void shouldMapMessageToSimpleMessageDTONotShorteningTextToSummary() {
    Message message =
        Message.builder()
            .id(NEWS_ID)
            .publishedAt(NEWS_PUBLISHED_AT)
            .title(NEWS_TITLE)
            .text(NEWS_SUMMARY)
            .build();
    SimpleMessageDto simpleMessageDTO = messageMapper.messageToSimpleMessageDto(message);
    assertThat(simpleMessageDTO).isNotNull();
    assertThat(simpleMessageDTO.getId()).isEqualTo(1L);
    assertThat(simpleMessageDTO.getPublishedAt()).isEqualTo(NEWS_PUBLISHED_AT);
    assertThat(simpleMessageDTO.getTitle()).isEqualTo(NEWS_TITLE);
    assertThat(simpleMessageDTO.getSummary()).isEqualTo(NEWS_SUMMARY);
  }

  @Test
  public void shouldMapMessageToDetailedMessageDTO() {
    Message message =
        Message.builder()
            .id(NEWS_ID)
            .publishedAt(NEWS_PUBLISHED_AT)
            .title(NEWS_TITLE)
            .text(NEWS_TEXT)
            .build();
    DetailedMessageDto detailedMessageDTO = messageMapper.messageToDetailedMessageDto(message);
    assertThat(detailedMessageDTO).isNotNull();
    assertThat(detailedMessageDTO.getId()).isEqualTo(1L);
    assertThat(detailedMessageDTO.getPublishedAt()).isEqualTo(NEWS_PUBLISHED_AT);
    assertThat(detailedMessageDTO.getTitle()).isEqualTo(NEWS_TITLE);
    assertThat(detailedMessageDTO.getText()).isEqualTo(NEWS_TEXT);
  }

  @Test
  public void shouldMapDetailedMessageDTOToMessage() {
    DetailedMessageDto detailedMessageDTO =
        DetailedMessageDto.builder()
            .id(1L)
            .publishedAt(NEWS_PUBLISHED_AT)
            .title(NEWS_TITLE)
            .text(NEWS_TEXT)
            .build();
    Message message = messageMapper.detailedMessageDtoToMessage(detailedMessageDTO);
    assertThat(message).isNotNull();
    assertThat(message.getId()).isEqualTo(1L);
    assertThat(message.getPublishedAt()).isEqualTo(NEWS_PUBLISHED_AT);
    assertThat(message.getTitle()).isEqualTo(NEWS_TITLE);
    assertThat(message.getText()).isEqualTo(NEWS_TEXT);
  }

  @Configuration
  @ComponentScan(basePackages = "at.ac.tuwien.sepm.groupphase.backend.entity.mapper")
  public static class MessageMapperTestContextConfiguration {}
}
