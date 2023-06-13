package zeebe.camunda.introduction;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.stereotype.Component;

import static java.lang.System.out;

@Component
public class MessageService {

    @JobWorker(type = "handling_message")
    public void logMessage(@Variable String text_field){
        out.println(text_field);
    }

}
