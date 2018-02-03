package com.learncamel.launch;

import com.learncamel.routes.Rest2DBRoute;
import org.apache.camel.Main;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class AppLauncher {

    public static void main(String[] args) throws Exception {

        String url = "jdbc:postgresql://localhost:5432/localDB";

        Main main = new Main();

        main.bind("myDataSource",setupDataSource(url));  //map based registry
        main.addRouteBuilder(new Rest2DBRoute());

        System.out.println("Starting Camel JMS to DB Route.");

        main.run();


    }


    private static DataSource setupDataSource(String connectURI) {
        BasicDataSource ds = new BasicDataSource();
        ds.setUsername("postgres");
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setPassword("admin");
        ds.setUrl(connectURI);
        return ds;
    }
}
