package demo.workers;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClaimProcessProcessClaimWorker implements WorkerInterface{

    private final static Logger LOGGER = Logger.getLogger(ClaimProcessProcessClaimWorker.class.getName());

    public void executeBusinessLogic(ExternalTask externalTask, ExternalTaskService externalTaskService) {
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
    }
}
