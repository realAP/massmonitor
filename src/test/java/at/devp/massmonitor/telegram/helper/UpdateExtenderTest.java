package at.devp.massmonitor.telegram.helper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

class UpdateExtenderTest {

  private static Stream<Arguments> createCommand() {
    return Stream.of(
        Arguments.of(createUpdate(true, false), true),
        Arguments.of(createUpdate(false, true), true));
  }

  private static Update createUpdate(final boolean withCommand, final boolean withEditedCommand) {
    final var update = new Update();

    final var message = new Message();
    final var messageEntity = new MessageEntity(EntityType.BOTCOMMAND, 0, 5);
    message.setEntities(List.of(messageEntity));
    message.setText("/text");

    if (withCommand) {
      update.setMessage(message);
    }
    if (withEditedCommand) {
      update.setEditedMessage(message);
    }
    return update;
  }

  @Test
  void whenIsCommandFromEditMessageGivenEditedMessageWithCommandThenReturnTrue() {
    final var update = createUpdate(false, true);

    // TODO unclean, need to initialize it in class not method
    final var underTest = new UpdateExtender(update);

    boolean result = underTest.isCommandFromEditMessage();

    assertThat(result, is(true));
  }

  @Test
  void whenIsCommandFromMessageGivenMessageWithCommandThenReturnTrue() {
    final var update = createUpdate(true, false);

    // TODO unclean, need to initialize it in class not method
    final var underTest = new UpdateExtender(update);

    boolean result = underTest.isCommandFromMessage();

    assertThat(result, is(true));
  }

  @ParameterizedTest()
  @MethodSource("createCommand")
  void whenHasACommandGivenBothKindsOfMessageThenReturnTrue(
      final Update update, final boolean expectedResult) {
    // TODO unclean, need to initialize it in class not method
    final var underTest = new UpdateExtender(update);

    boolean result = underTest.hasACommand();

    assertThat(result, is(expectedResult));
  }

  @Test
  void whenHasACommandNoCommandGivenThenReturnFalse() {
    final var update = createUpdate(false, false);

    // TODO unclean, need to initialize it in class not method
    final var underTest = new UpdateExtender(update);

    boolean result = underTest.hasACommand();

    assertThat(result, is(false));
  }

  @Test
  void whenIsCommandFromEditMessageGivenNoCommandThenReturnFalse() {
    final var update = createUpdate(false, false);

    // TODO unclean, need to initialize it in class not method
    final var underTest = new UpdateExtender(update);

    boolean result = underTest.isCommandFromEditMessage();

    assertThat(result, is(false));
  }

  @Test
  void whenIsCommandFromEditMessageGivenCommandFromMessageThenReturnFalse() {
    final var update = createUpdate(true, false);

    // TODO unclean, need to initialize it in class not method
    final var underTest = new UpdateExtender(update);

    boolean result = underTest.isCommandFromEditMessage();

    assertThat(result, is(false));
  }

  @Test
  void whenIsCommandFromMessageGivenNoCommandThenReturnFalse() {
    final var update = createUpdate(false, false);

    // TODO unclean, need to initialize it in class not method
    final var underTest = new UpdateExtender(update);

    boolean result = underTest.isCommandFromMessage();

    assertThat(result, is(false));
  }

  @Test
  void whenIsCommandFromMessageGivenCommandFromEditMessageThenReturnFalse() {
    final var update = createUpdate(false, true);

    // TODO unclean, need to initialize it in class not method
    final var underTest = new UpdateExtender(update);

    boolean result = underTest.isCommandFromMessage();

    assertThat(result, is(false));
  }

  @Test
  void whenGetCommandGivenCommandFromMessageThenReturnMessageEntity() {
    final var update = createUpdate(true, false);

    final var underTest = new UpdateExtender(update);

    final var result = underTest.getCommand();

    assertThat(update.getMessage().getEntities().stream().findFirst().get(), is(result));
  }

  @Test
  void whenGetCommandGivenCommandFromEditMessageThenReturnMessageEntity() {
    final var update = createUpdate(false, true);

    final var underTest = new UpdateExtender(update);

    final var result = underTest.getCommand();

    assertThat(update.getEditedMessage().getEntities().stream().findFirst().get(), is(result));
  }

  @Test
  void whenGetCommandGivenNoCommandThenReturnNull() {
    final var update = createUpdate(false, false);

    final var underTest = new UpdateExtender(update);

    final var result = underTest.getCommand();

    assertThat(result, nullValue());
  }
}
