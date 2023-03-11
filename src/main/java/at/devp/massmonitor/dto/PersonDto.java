package at.devp.massmonitor.dto;

import lombok.Data;

@Data
public class PersonDto {
  private String userName;
  private Integer creationTime;
  private String weight;
  private Integer messageId;
}
