package cz.cimbalek.roi.fb.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

/**
 * @author cimbalek
 */
@SpringBootApplication
public class RoiFbApp extends SpringBootServletInitializer {

    /**
     * @param args
     */
    public static void main(String args[]) {

        RoiFbApp app = new RoiFbApp();

        ConfigurableApplicationContext ctx = app
            .configure(new SpringApplicationBuilder(RoiFbApp.class))
            .run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RoiFbApp.class);
    }

}
