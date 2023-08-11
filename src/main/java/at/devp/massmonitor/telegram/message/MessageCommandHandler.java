package at.devp.massmonitor.telegram.message;

import at.devp.massmonitor.business.action.CreateWeightConsumer;
import at.devp.massmonitor.crud.CrudType;
import at.devp.massmonitor.dto.PersonDto;
import at.devp.massmonitor.dto.PersonDtoFactory;
import at.devp.massmonitor.telegram.MessageSender;
import at.devp.massmonitor.telegram.commands.Commands;
import at.devp.massmonitor.telegram.commands.CommandsParser;
import at.devp.massmonitor.telegram.commands.CrudTypeDetector;
import at.devp.massmonitor.telegram.helper.UpdateExtender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.xml.bind.ValidationException;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class MessageCommandHandler implements HandlerIdentifier {
  private final CommandsParser commandsParser;
  private final CrudTypeDetector crudTypeDetector;
  private final PersonDtoFactory personDtoFactory;
  private final CreateWeightConsumer createWeightConsumer;

  private final MessageSender messageSender;


  public void consume(final UpdateExtender extendedUpdate, final Consumer<SendMessage> sendMessageConsumer) {
    final CrudType crudType = crudTypeDetector.getType(extendedUpdate);
    final Commands commands = commandsParser.getCommand(extendedUpdate);
    final String commandArgument = commandsParser.getArgumentOfCommand(extendedUpdate);

    if (Objects.equals(commands, Commands.WEIGHT) && CrudType.CREATE.equals(crudType)) {
      // build entity dto and call business logic
      try {
        final PersonDto personDto = personDtoFactory.create(extendedUpdate, commandArgument);
        createWeightConsumer.createWeight(personDto);
        messageSender.informUser(
            extendedUpdate.getUpdate().getMessage().getMessageId(),
            extendedUpdate.getUpdate().getMessage().getChatId().toString(),
            sendMessageConsumer,
            "created weight"
        );
      } catch (ValidationException ve) {
        messageSender.informUser(
            extendedUpdate.getUpdate().getMessage().getMessageId(),
            extendedUpdate.getUpdate().getMessage().getChatId().toString(),
            sendMessageConsumer,
            "input is not a valid weight"
        );
      }
    }

    // TODO use this information to create an Object which hold needed information
    //  to create from this point what the business logic need, i am tired

  }

  @Override
  public String getIdentifier() {
    return "CommandHandler";
  }
}
