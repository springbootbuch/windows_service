package de.springbootbuch.windows;

import javax.management.InstanceNotFoundException;
import javax.management.remote.JMXConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(final String... args) throws Exception {
        if (args.length != 2 || !args[0].trim().equalsIgnoreCase("stopService")) {
            SpringApplication.run(Application.class, args);
        } else {
            try (JMXConnector connector = AdminClient.connect(Integer.parseInt(args[1]))) {
                new AdminClient(connector.getMBeanServerConnection(), AdminClient.DEFAULT_OBJECT_NAME).stop();
            } catch (InstanceNotFoundException ex) {
                throw new IllegalStateException("Spring application lifecycle JMX bean not "
                        + "found, could not stop application gracefully", ex);
            }
        }
    }
}
