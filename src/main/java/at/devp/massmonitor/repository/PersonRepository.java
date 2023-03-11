package at.devp.massmonitor.repository;

import at.devp.massmonitor.entitiy.Person;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  Optional<Person> findPeopleByMessageId(final Integer messageId);

  void deletePersonByMessageId(final Integer messageId);
}
