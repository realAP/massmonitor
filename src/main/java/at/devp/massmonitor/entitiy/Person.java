package at.devp.massmonitor.entitiy;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column private String userName;

  @Column private Integer creationTime;

  @Column private String weight;

  @Column private Integer messageId;
}
