package at.devp.massmonitor.telegram.commands;

import at.devp.massmonitor.telegram.helper.UpdateExtender;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class CommandsParser {

  public String getCommandArgument(final Update update) {
    //        String commandArgument = null;
    //        if (isCommandFromMessage(update)) {
    //            commandArgument = getArgumentOfCommand(update.getMessage());
    //        } else if (isCommandFromEditMessage(update)) {
    //            commandArgument = getArgumentOfCommand(update.getEditedMessage());
    //        }
    return "";
  }

  @Nullable
  public Commands getCommand(UpdateExtender update) {
    final var telegramCommand = update.getCommand();
    if (telegramCommand.getText().equals(Commands.WEIGHT.getCommand())) {
      return Commands.WEIGHT;
    }
    //        final var command = messageEntityConverter.toDomainCommand(messageEntity);
    //        else if (isCommandFromEditMessage(update)) {
    //            commandArgument = getArgumentOfCommand(update.getEditedMessage());
    //        }
    //        return commandArgument;
    //        final var command = message.getEntities().stream().findFirst().get();
    return null;
  }

  public String getArgumentOfCommand(final UpdateExtender updateExtender) {
    final var command = updateExtender.getCommand();
    final var message = updateExtender.getUpdate().getMessage();
    return message.getText().substring(command.getLength() + 1);
  }

  public String getArgumentOfEditCommand(UpdateExtender updateExtender) {
    final var command = updateExtender.getCommand();
    final var message = updateExtender.getUpdate().getEditedMessage();
    return message.getText().substring(command.getLength() + 1);
  }

  public Commands getEditCommand(UpdateExtender extendedUpdate) {
    final var telegramCommand = extendedUpdate.getCommand();
    if (telegramCommand.getText().equals(Commands.WEIGHT.getCommand())) {
      return Commands.WEIGHT;
    }
    return null;
  }
}
