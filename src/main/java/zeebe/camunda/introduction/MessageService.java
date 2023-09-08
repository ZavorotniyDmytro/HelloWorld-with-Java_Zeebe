package zeebe.camunda.introduction;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MessageService {
    static Logger logger = Logger.getLogger(MessageService.class.getName());

    @Autowired
    private ZeebeClient client;

    @JobWorker(type = "throw-message", fetchAllVariables = true, autoComplete = false)
    public void throwMessage(ActivatedJob job, JobClient jobClient,
                             @Variable String messageName,
                             @Variable String sessionId){
        // System.out.println("throw-message " + job.getVariablesAsMap());

        Map<String, Object> variables = job.getVariablesAsMap();
        logger.log(Level.INFO,
                "[throw-message:" + variables.toString() + ", processInstanceID=" +
                        job.getProcessInstanceKey() + "]");

        client.newPublishMessageCommand()
                .messageName(messageName)
                .correlationKey(
                        sessionId
                )
                .variables(variables)
                .send()
                .join();
        jobClient.newCompleteCommand(job.getKey()).variables(variables).send().join();
    }

    @JobWorker(type = "key-generate", fetchAllVariables = true, autoComplete = false)
    public void keyGenerate(ActivatedJob job, JobClient jobClient){

        Map<String, Object> variables = job.getVariablesAsMap();
        variables.put("sessionId", UUID.randomUUID());

       // System.out.println("key-generate " + variables);
        logger.log(Level.INFO,
                "[key-generate:" + variables.toString() + ", processInstanceID=" +
                        job.getProcessInstanceKey() + "]");

        jobClient.newCompleteCommand(job).variables(variables).send().join();
    }

}
