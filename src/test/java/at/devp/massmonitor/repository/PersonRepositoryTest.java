package at.devp.massmonitor.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

import at.devp.massmonitor.entitiy.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PersonRepositoryTest {

  private Person person;

  @Autowired private PersonRepository underTest;

  @BeforeEach
  void setUp() {
    person = new Person();
    person.setWeight("800");
    person.setMessageId(1337);
    person.setCreationTime(12345);
    person.setUserName("userName123");
    underTest.save(person);
  }

  @Test
  void findPeopleByMessageId() {
    final var result = underTest.findPeopleByMessageId(1337);

    assertThat(result.get(), is(person));
  }

  @Test
  void deletePersonByMessageId() {
    underTest.deletePersonByMessageId(1337);

    assertThat(underTest.findAll(), empty());
  }
}
