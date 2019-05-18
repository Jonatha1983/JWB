package org.gafner.jwbc;

import exceptions.JWBLoginException;
import jwb_api.JWBLoginServiceI;
import jwb_api.JWBUserData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@SpringBootApplication
public class TestRMIInitial {

    @Bean
    RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://localhost:1099/JWBLoginServiceI");
        rmiProxyFactory.setServiceInterface(JWBLoginServiceI.class);
        return rmiProxyFactory;
    }

    public static void main(String[] args) throws JWBLoginException {
        JWBLoginServiceI service = SpringApplication.run(TestRMIInitial.class, args).getBean(JWBLoginServiceI.class);
        JWBUserData userDataI = service.login("Jonathan Gafner");
        System.out.println(userDataI);
    }

}
