package websockets;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import websockets.config.WebSocketConfig;
import websockets.resources.BroadcastServlet;
import websockets.resources.BroadcasterResource;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Hello world!
 *
 */
public class App extends Application<WebSocketConfig>
{
    public static void main( String[] args ) throws Exception
    {
       new App().run(args);
    }

    @Override
    public String getName() {
        return "domoticServer";
    }

    @Override
    public void initialize(Bootstrap<WebSocketConfig> bootstrap) {
    }


    @Override
    public void run(WebSocketConfig configuration,
            Environment environment)  throws IOException {

        environment.jersey().register(new BroadcasterResource(environment.getObjectMapper()));

        environment.getApplicationContext().getServletHandler().addServletWithMapping(BroadcastServlet.class, "/sockets/*");

        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        //filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter("preflightMaxAge", "5184000"); // 2 months
        filter.setInitParameter("allowCredentials", "true");

    }



}
