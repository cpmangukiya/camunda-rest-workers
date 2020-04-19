package demo;

import org.camunda.bpm.client.ExternalTaskClient;

import java.awt.*;
import java.net.URI;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoanApprovalWorker {

    private final static Logger LOGGER = Logger.getLogger(LoanApprovalWorker.class.getName());

    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8880/engine-rest")
               //  .asyncResponseTimeout(10000) long polling timeout
                .build();

        // subscribe to an external task topic as specified in the process
        client.subscribe("process-loan")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here

                    // Get a process variable
                    String item = (String) externalTask.getVariable("item");
                    Long amount = (Long) externalTask.getVariable("amount");

                    LOGGER.info("Charging credit card with an amount of '" + amount + "'€ for the item '" + item + "'...");

                    try {
                        Desktop.getDesktop().browse(new URI("https://docs.camunda.org/get-started/quick-start/complete"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();

        ExternalTaskClient client2 = ExternalTaskClient.create()
                .baseUrl("http://localhost:8880/engine-rest")
                //  .asyncResponseTimeout(10000) long polling timeout
                .build();

        // subscribe to an external task topic as specified in the process
        client2.subscribe("validate-claim")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    boolean isValid = true;
                    LOGGER.info("Validating claim started");
                    try {
                        // Put your business logic here

                        // Get a process variable
                        String custId = (String) externalTask.getVariable("custId");
                        Long amount = (Long) externalTask.getVariable("amount");

                        if (custId == null || amount == null) {
                            isValid = false;
                        }

                        LOGGER.info("Validating customer and amount. custId '" + custId + "' and amount '" + amount + "'...");

                    } catch(Exception e){
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                        isValid = false;
                    }
                    // Complete the task
                    externalTaskService.complete(externalTask, Collections.singletonMap("isValid", isValid));
                })
                .open();


        ExternalTaskClient client3 = ExternalTaskClient.create()
                .baseUrl("http://localhost:8880/engine-rest")
                //  .asyncResponseTimeout(10000) long polling timeout
                .build();

        // subscribe to an external task topic as specified in the process
        client3.subscribe("process-claim")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {

                    LOGGER.info("Processing claim started");
                    try {
                        // Put your business logic here

                        // Get a process variable
                        String custId = (String) externalTask.getVariable("custId");
                        Long amount = (Long) externalTask.getVariable("amount");

                        LOGGER.info("Processing claim. custId '" + custId + "' and amount '" + amount + "'...");

                    } catch(Exception e){
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                    }
                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();
    }
}
