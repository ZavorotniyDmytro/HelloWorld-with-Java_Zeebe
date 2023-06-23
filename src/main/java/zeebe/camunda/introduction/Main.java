package zeebe.camunda.introduction;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientBuilder;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@EnableScheduling
//@Deployment(resources = "classpath:message-bpmn-diagram.bpmn")
public class Main {

    static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static ConsoleHandler handler = new ConsoleHandler();
    static ZeebeClient zeebeClient = null;

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

            zeebeClient = client;
            final DeploymentEvent deploymentEvent = deployDiagram(client);

            System.out.println("Deployment created with key: " + deploymentEvent.getKey());
            System.out.println("Deployment created with Processes: " + deploymentEvent.getProcesses());

//            ProcessInstanceEvent instance = createInstance(client);
//
//            System.out.println("Instance created with ProcessId: " + instance.getBpmnProcessId());
//            System.out.println("Instance created with ProcessDefinitionKey: " + instance.getProcessDefinitionKey());
//            System.out.println("Instance created with ProcessInstanceKey: " + instance.getProcessInstanceKey());

        }
    }

    static DeploymentEvent deployDiagram(ZeebeClient client){
        return client.newDeployResourceCommand()
                .addResourceFromClasspath("message-bpmn-diagram-v2.bpmn")
                .send()
                .join();
    }

    static ProcessInstanceEvent createInstance(ZeebeClient client){
        return client.newCreateInstanceCommand()
                .bpmnProcessId("MessageHandler")
                .latestVersion()
                .send()
                .join();
    }

}