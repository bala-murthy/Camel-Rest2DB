package com.learncamel.routes;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class RestRouteTest extends CamelTestSupport {
    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RestRoute();
    }

    @Test
    public void testRestRoute() throws Exception{
        String response = template.requestBody("direct:restInput","USA",String.class);
        System.out.println("Response Received is :: "+response);
        assertNotNull(response);
    }

}
