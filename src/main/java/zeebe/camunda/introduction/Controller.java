package zeebe.camunda.introduction;

import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    ZeebeClient client;

    @GetMapping("/")
    public String onBroad(){
        client.newActivateJobsCommand().jobType("handling_message");
        System.out.println("Success");
        return "Success";
    }
}
