package com.learncamel.routes;

import com.learncamel.processors.ExceptionProcessor;
import com.learncamel.processors.InputProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.postgresql.util.PSQLException;

public class Rest2DBRoute extends RouteBuilder {

    public void configure() throws Exception {

        onException(PSQLException.class, Exception.class).handled(true).log("Exception While inserting messages.").process(new ExceptionProcessor());

        from("timer:learnTimer?period=10s")
                .to("log:?level=INFO&showBody=true")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.HTTP_URI, simple("http://restcountries.eu/rest/v2/alpha/in"))
                .to("http://restcountries.eu/rest/v2/alpha/in").convertBodyTo(String.class)
                .to("log:?level=INFO&showBody=true")
                .process(new InputProcessor())
                .to("jdbc:myDataSource")
                .to("sql:select * from country_capital?dataSource=myDataSource")
                .to("direct:dbOutput");
    }
}
