package com.learncamel.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.ArrayList;

public class Rest2DBRouteTest extends CamelTestSupport{

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        System.out.println("Route Builder Created");
        return new Rest2DBRoute();
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/localDB";
        DataSource dataSource = setupDataSource(url);

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("myDataSource",dataSource);
        System.out.println("Data source Created and set in the registry");

        CamelContext context = new DefaultCamelContext(registry);

        return context;
    }

    private DataSource setupDataSource(String url) {
        BasicDataSource ds = new BasicDataSource();
        ds.setUsername("postgres");
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setPassword("admin");
        ds.setUrl(url);
        return ds;
    }

    @Test
    public void testRest2DBRoute(){
        ArrayList responseList =  consumer.receiveBody("direct:dbOutput", ArrayList.class);
        System.out.println("responseList : " + responseList.size());
        assertNotEquals(0, responseList.size());

    }


    // Enable this test case if you want to test incorrect endpoint URLS. If you enable this now the previous positive test case will fail
    //@Test
    /*public void rest2dbroute_exception(){

        ArrayList responseList =  consumer.receiveBody("timer:learnTimer", ArrayList.class);
        assertNull(responseList);

    }*/
}
