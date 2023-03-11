package at.devp.massmonitor.telegram.commands;

public enum Commands {
  ACTIVITY("/activity"),

  WEIGHT("/weight"),

  UNKNOWN("/UNKNOWN");

  private final String value;

  Commands(String value) {
    this.value = value;
  }

  public String getCommand() {
    return value;
  }
}
