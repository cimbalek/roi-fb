package cz.cimbalek.roi.fb.rest.facade;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {

        register(UsersRest.class);
    }

}
