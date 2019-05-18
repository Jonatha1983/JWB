package hello;

import exceptions.JWBLoginException;
import jwb_api.JWBLoginServiceI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;


@SpringBootApplication
public class JWBServerMain {

    @Bean
    JWBLoginServiceI loginService() throws JWBLoginException {
        return new JWBLoginServerImpl();
    }

    @Bean
    RmiServiceExporter exporter(JWBLoginServiceI impl) {

        // Expose a service via RMI. Remote obect URL is:
        // rmi://<HOST>:<PORT>/<SERVICE_NAME>
        // 1099 is the default port

        Class<JWBLoginServiceI> serviceInterface = JWBLoginServiceI.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(impl);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(JWBServerMain.class, args);
    }

}