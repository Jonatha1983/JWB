package com.gafner.jwb;

import com.gafner.jwb.api.FXMLViews;
import com.gafner.jwb.api.model.UserAPI;
import com.gafner.jwb.api.service.group.GroupConnectionService;
import com.gafner.jwb.api.service.users.UserConnectionService;
import com.gafner.jwb.config.StageManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class JWBApplication extends Application {


    protected ConfigurableApplicationContext context;
    protected StageManager stageManager;

    @Override
    public void init() {
        ApplicationContextInitializer<GenericApplicationContext> initializer =
                applicationContext -> {
                    SecurityContextHolder.setStrategyName("MODE_GLOBAL");
                    AnonymousAuthenticationToken auth = new AnonymousAuthenticationToken(
                            "anonymous",
                            "anonymous",
                            AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

                    SecurityContextHolder.getContext().setAuthentication(auth);
                    applicationContext.registerBean(JWBApplication.class, () -> JWBApplication.this);
                    applicationContext.registerBean(Parameters.class, this::getParameters);
                };

        this.context = new SpringApplicationBuilder()
                .sources(JWBApplication.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));

    }

    @Bean
    RmiProxyFactoryBean userConnectionService() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://localhost:1099/UserConnectionService");
        rmiProxyFactory.setServiceInterface(UserConnectionService.class);
        return rmiProxyFactory;
    }

    @Bean
    RmiProxyFactoryBean groupConnectionService() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://localhost:1099/GroupConnectionService");
        rmiProxyFactory.setServiceInterface(GroupConnectionService.class);
        return rmiProxyFactory;
    }

    public static void main(String[] args) {
        launch(JWBApplication.class, args);
    }

    @Override
    public void start(Stage primaryStage) {
        stageManager = context.getBean(StageManager.class, primaryStage);
        stageManager.switchScene(FXMLViews.HOME);
    }

    @Override
    public void stop() throws Exception {

        this.context.close();
        Platform.exit();
        System.exit(0);
    }

}
