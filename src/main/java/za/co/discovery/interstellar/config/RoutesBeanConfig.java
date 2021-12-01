package za.co.discovery.interstellar.config;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * This configuration is for the SOAP service exposed for shortest path algorithm.
 */
@EnableWs
@Configuration
public class RoutesBeanConfig {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(applicationContext);
        messageDispatcherServlet.setTransformSchemaLocations(true);
        return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
    }


    @Bean(name = "routes")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema routeSchema) {
        DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
        defaultWsdl11Definition.setPortTypeName("RoutesPort");
        defaultWsdl11Definition.setTargetNamespace("http://discovery.co.za.interstellar/routes");
        defaultWsdl11Definition.setLocationUri("/ws");
        defaultWsdl11Definition.setSchema(routeSchema);
        return defaultWsdl11Definition;
    }

    @Bean
    public XsdSchema routesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("route-details.xsd"));
    }

}
