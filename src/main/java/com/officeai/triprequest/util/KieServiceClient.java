package com.officeai.triprequest.util;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.model.ServiceResponse;

public class KieServiceClient {
  private static final String DEPLOY_UNIT_NAME = "com.officeai:TripRequest:1.0.10";
//  private static final String DEPLOY_UNIT_NAME = "TripRequest_1.0.10";
    private static final String KIE_SERVER_URL = "http://localhost:8180/kie-server/services/rest/server";
//  private static final String KIE_SERVER_URL = "http://localhost:8080/kie-server/services/rest/server";
  private static final String KIE_SERVER_USER = "kieserver";
  private static final String KIE_SERVER_PASSWORD = "kieserver1!";

  public static ExecutionResults execute(Command<?> command) {
    KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(KIE_SERVER_URL, KIE_SERVER_USER,
        KIE_SERVER_PASSWORD);
    config.setMarshallingFormat(MarshallingFormat.JSON);

    KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
    RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);
    ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(DEPLOY_UNIT_NAME, command);
    ExecutionResults results = response.getResult();
    return results;
  }
}
