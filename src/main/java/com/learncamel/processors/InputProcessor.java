package com.learncamel.processors;

import org.apache.camel.Exchange;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class InputProcessor implements org.apache.camel.Processor {

    public void process(Exchange exchange) throws Exception {

        String inComingJSON = (String) exchange.getIn().getBody();
        System.out.println("Incoming JSON Object from Test class is :: "+inComingJSON);

        //Create a JSON Parser, parse the incomingJSON object and cast it as JSON Object
        // From the casted JSON Object fetch the country name and capital and
        // Create the Insert SQL and Set as the ExchangeBody

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(inComingJSON);

        JSONObject jsonObject = (JSONObject) obj;

        String countryName = (String)jsonObject.get("name");
        String countryCapital = (String) jsonObject.get("capital");

        String insertSQL = "INSERT INTO public.country_capital values ( ".concat("'").concat(countryName).concat("'").concat(",").concat("'").concat(countryCapital).concat("'").concat(" )");
        System.out.println("Formed Insert SQL is  :: "+insertSQL);

        exchange.getIn().setBody(insertSQL);

    }
}
