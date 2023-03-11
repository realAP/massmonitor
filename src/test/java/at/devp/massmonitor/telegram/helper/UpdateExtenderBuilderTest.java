package at.devp.massmonitor.telegram.helper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;

class UpdateExtenderBuilderTest {

  private final UpdateExtenderBuilder underTest = new UpdateExtenderBuilder();

  @Test
  void whenGivenUpdateThenReturnUpdateExtender() {
    final var update = new Update();

    final var result = underTest.createUpdateExtender(update);

    assertThat(result, instanceOf(UpdateExtender.class));
  }

  @Test
  void whenGivenUpdateTheItIsContainedInUpdateExtender() {
    final var update = new Update();

    final var result = underTest.createUpdateExtender(update);

    assertThat(result.getUpdate(), sameInstance(update));
  }
}
