package zeebe.camunda.introduction;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientBuilder;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
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

        final String ADDRESS = "172.17.201.28:26500";

        logger.setLevel(Level.ALL);
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.info("SERVER STARTED");

        final ZeebeClientBuilder clientBuilder;
        clientBuilder = ZeebeClient.newClientBuilder().gatewayAddress(ADDRESS).usePlaintext();

        try (final ZeebeClient client = clientBuilder.build()) {

            final DeploymentEvent deploymentEvent =
                    client.newDeployResourceCommand()
                            .addResourceFromClasspath("message-bpmn-diagram.bpmn")
                            .send()
                            .join();

            System.out.println("Deployment created with key: " + deploymentEvent.getKey());
            System.out.println("Deployment created with Processes: " + deploymentEvent.getProcesses());

            // client.newCreateInstanceCommand().bpmnProcessId()
        }
    }
}