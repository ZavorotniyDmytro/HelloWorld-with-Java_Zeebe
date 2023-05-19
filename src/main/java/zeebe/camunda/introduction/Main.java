package zeebe.camunda.introduction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@Deployment(resources = "classpath:message-bpmn-diagram.bpmn")
public class Main {

    static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static ConsoleHandler handler = new ConsoleHandler();
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.run(args);
        logger.setLevel(Level.ALL);
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);

        logger.info("SERVER STARTED");
    }
}