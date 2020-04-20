package demo.workers;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;

@FunctionalInterface
public interface WorkerInterface {

    void executeBusinessLogic(ExternalTask externalTask, ExternalTaskService externalTaskService);
}
