package at.devp.massmonitor.entitiy;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column
  private String userName;

  @Column
  private Integer creationTime;

  // TODO make integer and add validation
  @Column
  private String weight;

  @Column
  private Integer messageId;
}
