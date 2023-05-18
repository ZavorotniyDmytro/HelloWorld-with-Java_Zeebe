package zeebe.camunda.introduction;

import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.ZeebeDeployment;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.camunda.zeebe.spring.client.actuator.ZeebeActuatorConfiguration;

import java.time.Duration;
import java.util.Collections;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.out;

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

//        final DeploymentEvent deployment =
//                client.newDeployResourceCommand()
//                        .addResourceFromClasspath("print_service_diagram.bpmn")
//                        .send()
//                        .join();
//        out.println(deployment.getProcesses());
//        final int version = deployment.getProcesses().get(0).getVersion();
//        System.out.println("Process deployed. Version: " + version);
//
    }
}