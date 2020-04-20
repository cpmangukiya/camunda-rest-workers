package demo.workers;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClaimProcessValidateClaimWorker implements WorkerInterface{

    private final static Logger LOGGER = Logger.getLogger(ClaimProcessValidateClaimWorker.class.getName());

    public void executeBusinessLogic(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        LOGGER.info("Validating claim started");
        boolean isValid = true;
        try {
            // Some business logic here

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
    }
}
