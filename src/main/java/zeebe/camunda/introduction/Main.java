package zeebe.camunda.introduction;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientBuilder;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


@SpringBootApplication
@EnableScheduling
// @Deployment(resources = "classpath:message-start.bpmn")
public class Main // implements CommandLineRunner
{
    static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static ConsoleHandler handler = new ConsoleHandler();

    @Autowired
    private ZeebeClient client;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.run(args);

//        logger.setLevel(Level.ALL);
//        handler.setLevel(Level.ALL);
//        logger.addHandler(handler);

//        final String ADDRESS = "172.17.201.28:26500";
//        final ZeebeClientBuilder clientBuilder;
//        clientBuilder = ZeebeClient.newClientBuilder().gatewayAddress(ADDRESS).usePlaintext();

//        try (final ZeebeClient client = clientBuilder.build()) {
//            zeebeClient = client;
//            final DeploymentEvent deploymentEvent = deployDiagram(client);
//
//            System.out.println("Deployment created with key: " + deploymentEvent.getKey());
//            System.out.println("Deployment created with Processes: " + deploymentEvent.getProcesses());
//
//            ProcessInstanceEvent instance = createInstance(client);
//
//            System.out.println("Instance created with ProcessId: " + instance.getBpmnProcessId());
//            System.out.println("Instance created with ProcessDefinitionKey: " + instance.getProcessDefinitionKey());
//            System.out.println("Instance created with ProcessInstanceKey: " + instance.getProcessInstanceKey());
//        }
    }

    static DeploymentEvent deployDiagram(ZeebeClient client){
        return client.newDeployResourceCommand()
                .addResourceFromClasspath("using-fs-connector-full-operations.bpmn")
                .send()
                .join();
    }

    static ProcessInstanceEvent createInstance(ZeebeClient client){
        return client.newCreateInstanceCommand()
                .bpmnProcessId("FS-connector-full")
                .latestVersion()
                .send()
                .join();
    }

//    @JobWorker(type = "print-local-msg")
//    public void logMessage(ActivatedJob job, JobClient jobClient){
//        System.out.println(job.getVariables());
//        jobClient.newCompleteCommand(job).send();
//    }
//
//    @JobWorker(type = "throw-message")
//    public void throwMessage(ActivatedJob job, JobClient jobClient, @Variable String messageName){
//        System.out.println(job.getVariables());
//
//        client.newPublishMessageCommand()
//                .messageName(messageName)
//                .correlationKey("end-1")
//                .send();
//        jobClient.newCompleteCommand(job).send();
//    }

//    @Override
//    public void run(String... args) throws Exception {
//
//        client.newPublishMessageCommand()
//                .messageName("start")
//                .correlationKey("start-1")
//                .send()
//                .exceptionally(throwable -> {
//                    throw new RuntimeException("could not publish message", throwable);
//                });
//    }
}