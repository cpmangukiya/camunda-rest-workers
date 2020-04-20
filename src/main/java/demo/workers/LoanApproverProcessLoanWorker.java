package demo.workers;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;

import java.awt.*;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoanApproverProcessLoanWorker implements WorkerInterface {

    private final static Logger LOGGER = Logger.getLogger(LoanApproverProcessLoanWorker.class.getName());

    public void executeBusinessLogic(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        try {
            // Some business logic here

            // Get a process variable
            String item = (String) externalTask.getVariable("item");
            Long amount = (Long) externalTask.getVariable("amount");

            LOGGER.info("Charging credit card with an amount of '" + amount + "'€ for the item '" + item + "'...");

            Desktop.getDesktop().browse(new URI("https://docs.camunda.org/get-started/quick-start/complete"));

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        // Complete the task
        externalTaskService.complete(externalTask);
    }
}
