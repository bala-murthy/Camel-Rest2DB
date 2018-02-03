package com.learncamel.routes;

import com.learncamel.processors.InputProcessor;
import org.apache.camel.builder.RouteBuilder;

public class MockRest2DBRoute extends RouteBuilder {

    public void configure() throws Exception {
        from("direct:mockRESTInput").
                to("log:?level=INFO&showBody=true").
                process(new InputProcessor()).
                to("jdbc:myDataSource").
                to("sql:select * from country_capital?dataSource=myDataSource").
                to("log:?level=INFO&showBody=true");

    }
}
