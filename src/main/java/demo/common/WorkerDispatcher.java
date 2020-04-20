package demo.common;

import demo.workers.ClaimProcessProcessClaimWorker;
import demo.workers.ClaimProcessValidateClaimWorker;
import demo.workers.LoanApproverProcessLoanWorker;
import demo.workers.WorkerInterface;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Logger;

public class WorkerDispatcher {

    @Value( "${bpmRestEndpoint}" )
    private static String bpmRestEndpoint = "http://localhost:8880/engine-rest";

    private final static Logger LOGGER = Logger.getLogger(WorkerDispatcher.class.getName());

    public static void initializeWorkers() {
        startWorker("process-loan", new LoanApproverProcessLoanWorker());
        startWorker("validate-claim", new ClaimProcessValidateClaimWorker());
        startWorker("process-claim", new ClaimProcessProcessClaimWorker());
        LOGGER.info("Workers are ready.");
    }

    private static void startWorker(String entityName, WorkerInterface workerInterface) {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(bpmRestEndpoint)
                //  .asyncResponseTimeout(10000) long polling timeout
                .build();

        // subscribe to an external task topic as specified in the process
        client.subscribe(entityName)
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler(workerInterface::executeBusinessLogic)
                .open();
    }
}
