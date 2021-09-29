package com.revature.put_sets;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.put_sets.models.Set;
import com.revature.put_sets.models.SetDto;
import com.revature.put_sets.models.Tag;
import com.revature.put_sets.repositories.SetRepo;
import com.revature.put_sets.repositories.TagRepo;

import java.util.List;
import java.util.Map;

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
            LambdaLogger logger = context.getLogger();
            logger.log("RECEIVED EVENT: " + requestEvent + "\n");

            APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

            /* Get set id from path parameters */
            Map<String, String> pathParameters = requestEvent.getPathParameters();
            String id = pathParameters.get("id");

            if(id == null) { // id was not given [400]
                responseEvent.setStatusCode(400);
                return responseEvent;
            }

            System.out.println("ID: " + id);

//            Set updatedSet = new Set();
            SetDto updatedSetDto = new SetDto();

            try {
                updatedSetDto = mapper.fromJson(requestEvent.getBody(), SetDto.class);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println(updatedSetDto);

            // Verify tag compliance
            List<Tag> verifiedTags = tagRepo.findTags(updatedSetDto.getTags());
            logger.log("TAGS: " + verifiedTags + "\n");

            // TODO: Complete and test tag verification
//            if(!verifiedTags.containsAll(updatedSetDto.getTags())) { // One or more tags are not sanctioned [400]
//                responseEvent.setStatusCode(400);
//                return responseEvent;
//            }

            // TEST QUERY
            List<Set> testList = setRepo.getSets();
            System.out.println("Test List: " + testList);

            System.out.println(requestEvent.getBody());
            System.out.println("Hello World!?");
            return responseEvent;
        }

}
