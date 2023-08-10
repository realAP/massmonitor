package at.devp.massmonitor.telegram.message;

import at.devp.massmonitor.business.action.UpdateWeightConsumer;
import at.devp.massmonitor.crud.CrudType;
import at.devp.massmonitor.dto.PersonDto;
import at.devp.massmonitor.dto.PersonDtoFactory;
import at.devp.massmonitor.telegram.commands.Commands;
import at.devp.massmonitor.telegram.commands.CommandsParser;
import at.devp.massmonitor.telegram.commands.CrudTypeDetector;
import at.devp.massmonitor.telegram.helper.UpdateExtender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class EditMessageCommandHandler implements HandlerIdentifier {
  private final CrudTypeDetector crudTypeDetector;
  private final CommandsParser commandsParser;
  private final PersonDtoFactory personDtoFactory;
  private final UpdateWeightConsumer updateWeightConsumer;


  public void consume(@NonNull UpdateExtender extendedUpdate, Consumer<SendMessage> sendMessageConsumer) {
    final CrudType crudType = crudTypeDetector.getType(extendedUpdate);
    final Commands commands = commandsParser.getEditCommand(extendedUpdate);
    final String commandArgument = commandsParser.getArgumentOfEditCommand(extendedUpdate);

    if (Commands.WEIGHT.equals(commands) && CrudType.UPDATE.equals(crudType)) {
      final PersonDto personDto = personDtoFactory.createFromEdited(extendedUpdate, commandArgument);
      updateWeightConsumer.updateWeight(personDto);
    }

    final var message = new SendMessage();
    message.setReplyToMessageId(extendedUpdate.getUpdate().getEditedMessage().getMessageId());
    message.setChatId(extendedUpdate.getUpdate().getEditedMessage().getChatId().toString());
    log.info("messagId: " + extendedUpdate.getUpdate().getEditedMessage().getMessageId());
    log.info("chatId: " + extendedUpdate.getUpdate().getEditedMessage().getChatId().toString());
    message.setText("updated weight");
    sendMessageConsumer.accept(message);
  }

  @Override
  public String getIdentifier() {
    return "EditHandler";
  }
}
