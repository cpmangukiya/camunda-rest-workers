package demo;

import demo.common.WorkerDispatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class WebServiceSpringBootApp {
    private final static Logger LOGGER = Logger.getLogger(WebServiceSpringBootApp.class.getName());

    public static void main( String[] args )
    {
        LOGGER.info( "Camunda Rest Workers starting ..." );
        SpringApplication.run(WebServiceSpringBootApp.class, args);
        WorkerDispatcher.initializeWorkers();
    }
}
