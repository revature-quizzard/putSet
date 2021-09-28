package com.revature.put_sets;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PutSetsHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

        private final TagRepo tagRepo;
        private final SetRepo setRepo;
        private static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();

        public PutSetsHandler() {
            tagRepo = new TagRepo();
            setRepo = new SetRepo();
        }

        public PutSetsHandler(TagRepo tagRepo, SetRepo setRepo) {
            this.tagRepo = tagRepo;
            this.setRepo = setRepo;
        }

        @Override
        public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
            APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
            System.out.println(requestEvent.getBody());
            System.out.println("Hello World!");
            return responseEvent;
        }

}
