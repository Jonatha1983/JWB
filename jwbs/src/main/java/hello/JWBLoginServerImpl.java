package hello;

import exceptions.JWBLoginException;
import jwb_api.JWBLoginServiceI;
import jwb_api.JWBUserData;
import org.springframework.context.annotation.Bean;


public class JWBLoginServerImpl implements JWBLoginServiceI {

    @Bean
    @Override
    public JWBUserData login(String id) throws JWBLoginException {
        return new JWBUserData(id);
    }
}

