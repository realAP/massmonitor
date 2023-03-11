package at.devp.massmonitor.telegram.commands;

import at.devp.massmonitor.crud.CrudType;
import at.devp.massmonitor.telegram.helper.UpdateExtender;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class CrudTypeDetector {

  @Nullable
  public CrudType getType(UpdateExtender update) {

    // does this makes sense?
    if (update == null) {
      return null;
    }

    if (update.isCommandFromMessage()) {
      final var command = update.getCommand();
      if (command.getText().equals(Commands.WEIGHT.getCommand())) {
        return CrudType.CREATE;
      }
    } else if (update.isCommandFromEditMessage()) {
      final var command = update.getCommand();
      if (command.getText().equals(Commands.WEIGHT.getCommand())) {
        return CrudType.UPDATE;
      }
    }
    return null;
  }
}
