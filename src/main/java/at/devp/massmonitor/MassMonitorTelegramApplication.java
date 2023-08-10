package at.devp.massmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MassMonitorTelegramApplication {

  public static void main(String[] args) {
    SpringApplication.run(MassMonitorTelegramApplication.class, args);
  }
}
