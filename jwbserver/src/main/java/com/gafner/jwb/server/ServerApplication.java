package com.gafner.jwb.server;

import com.gafner.jwb.api.model.UserAPI;
import com.gafner.jwb.api.service.group.GroupConnectionService;
import com.gafner.jwb.api.service.users.UserConnectionService;
import com.gafner.jwb.server.model.User;
import com.gafner.jwb.server.services.groups.GroupConnectionServiceImpl;
import com.gafner.jwb.server.services.users.UserConnectionServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;


@SuppressWarnings("unused")
@SpringBootApplication
public class ServerApplication {

    UserConnectionService userConnectionService() { return new UserConnectionServiceImpl(); }

    GroupConnectionService groupConnectionService() {
        return new GroupConnectionServiceImpl();
    }

    UserAPI user() {return new User(); }

    @Bean
    RmiServiceExporter exporterUserConnection(UserConnectionService userImpl) {

        Class<UserConnectionService> serviceInterface = UserConnectionService.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(userImpl);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }

    @Bean
    RmiServiceExporter exporterGroupConnection(GroupConnectionService groupImpl) {

        Class<GroupConnectionService> serviceInterface = GroupConnectionService.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(groupImpl);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
