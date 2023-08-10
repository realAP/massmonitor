package at.devp.massmonitor.telegram;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import at.devp.massmonitor.telegram.helper.UpdateExtender;
import at.devp.massmonitor.telegram.message.EditMessageCommandHandler;
import at.devp.massmonitor.telegram.message.MessageCommandHandler;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

@ExtendWith(MockitoExtension.class)
class TelegramMessageHandlerTest {

  @Mock private MessageCommandHandler messageCommandHandler;

  @Mock private EditMessageCommandHandler editMessageCommandHandler;

  @InjectMocks private TelegramMessageHandler underTest;

  private static Update createUpdateWithMessageCommand() {
    final var messageEntity = new MessageEntity(EntityType.BOTCOMMAND, 0, 10);
    final var message = new Message();
    message.setEntities(List.of(messageEntity));
    message.setText("text");

    final var update = new Update();
    update.setMessage(message);
    return update;
  }

  private static Update createUpdateWithEditedMessageCommand() {
    final var messageEntity = new MessageEntity(EntityType.BOTCOMMAND, 0, 4);
    final var message = new Message();
    message.setEntities(List.of(messageEntity));
    message.setText("text");

    final var update = new Update();
    update.setEditedMessage(message);
    return update;
  }

  @Test
  void whenConsumeGivenUpdateWithMessageCommandThenPassItToMessageCommandHandler() {
    final Update update = createUpdateWithMessageCommand();
    final var extendedUpdate = new UpdateExtender(update);

    // TODO: FIX ME
    Consumer<SendMessage> sendMessageConsumer = (SendMessage) -> System.out.println("FIX ME");
    underTest.consume(extendedUpdate, sendMessageConsumer);

    verify(messageCommandHandler).consume(extendedUpdate, sendMessageConsumer);
    verifyNoInteractions(editMessageCommandHandler);
  }

  @Test
  void whenConsumeGivenUpdateWithNoMessageCommandThenDoNotPassItToAnyCommandHandler() {
    final var update = new Update();
    final var message = new Message();
    update.setMessage(message);

    // TODO: FIX ME
    Consumer<SendMessage> sendMessageConsumer = (SendMessage) -> System.out.println("FIX ME");
    underTest.consume(new UpdateExtender(update), sendMessageConsumer);

    verifyNoInteractions(messageCommandHandler);
    verifyNoInteractions(editMessageCommandHandler);
  }

  @Test
  void whenConsumeGivenUpdateWithNoEditedMessageCommandThenDoNotPassItToAnyCommandHandler() {
    final var update = new Update();
    final var message = new Message();
    update.setEditedMessage(message);

    // TODO: FIX ME
    Consumer<SendMessage> sendMessageConsumer = (SendMessage) -> System.out.println("FIX ME");
    underTest.consume(new UpdateExtender(update), sendMessageConsumer);

    verifyNoInteractions(messageCommandHandler);
    verifyNoInteractions(editMessageCommandHandler);
  }

  @Test
  void whenConsumeGivenUpdateWithEditedMessageCommandThenPassItToEditCommandHandler() {
    final Update update = createUpdateWithEditedMessageCommand();
    final var extendedUpdate = new UpdateExtender(update);

    // TODO: FIX ME
    Consumer<SendMessage> sendMessageConsumer = (SendMessage) -> System.out.println("FIX ME");
    underTest.consume(extendedUpdate, sendMessageConsumer);

    verify(editMessageCommandHandler).consume(extendedUpdate, sendMessageConsumer);
    verifyNoInteractions(messageCommandHandler);
  }
}
