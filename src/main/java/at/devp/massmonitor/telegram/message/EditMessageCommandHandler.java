package at.devp.massmonitor.telegram.message;

import at.devp.massmonitor.business.action.UpdateWeightConsumer;
import at.devp.massmonitor.crud.CrudType;
import at.devp.massmonitor.dto.PersonDto;
import at.devp.massmonitor.dto.PersonDtoFactory;
import at.devp.massmonitor.telegram.MessageSender;
import at.devp.massmonitor.telegram.commands.Commands;
import at.devp.massmonitor.telegram.commands.CommandsParser;
import at.devp.massmonitor.telegram.commands.CrudTypeDetector;
import at.devp.massmonitor.telegram.helper.UpdateExtender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.xml.bind.ValidationException;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class EditMessageCommandHandler implements HandlerIdentifier {
  private final CrudTypeDetector crudTypeDetector;
  private final CommandsParser commandsParser;
  private final PersonDtoFactory personDtoFactory;
  private final UpdateWeightConsumer updateWeightConsumer;

  private final MessageSender messageSender;

  public void consume(@NonNull UpdateExtender extendedUpdate, Consumer<SendMessage> sendMessageConsumer) {
    final CrudType crudType = crudTypeDetector.getType(extendedUpdate);
    final Commands commands = commandsParser.getEditCommand(extendedUpdate);
    final String commandArgument = commandsParser.getArgumentOfEditCommand(extendedUpdate);

    if (Objects.equals(commands, Commands.WEIGHT) && CrudType.UPDATE.equals(crudType)) {
      // build entity dto and call business logic
      try {
        final PersonDto personDto = personDtoFactory.createFromEdited(extendedUpdate, commandArgument);
        updateWeightConsumer.updateWeight(personDto);
        messageSender.informUser(
            extendedUpdate.getUpdate().getEditedMessage().getMessageId(),
            extendedUpdate.getUpdate().getEditedMessage().getChatId().toString(),
            sendMessageConsumer,
            "updated weight"
        );
        // When error happens then the user should be informed
      } catch (ValidationException ve) {
        messageSender.informUser(
            extendedUpdate.getUpdate().getEditedMessage().getMessageId(),
            extendedUpdate.getUpdate().getEditedMessage().getChatId().toString(),
            sendMessageConsumer,
            "input is not a valid weight"
        );
      } catch (RuntimeException re) {
        messageSender.informUser(
            extendedUpdate.getUpdate().getEditedMessage().getMessageId(),
            extendedUpdate.getUpdate().getEditedMessage().getChatId().toString(),
            sendMessageConsumer,
            "input was not stored"
        );
      }
    }

  }

  @Override
  public String getIdentifier() {
    return "EditHandler";
  }
}
