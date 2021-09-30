package com.revature.put_sets;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.put_sets.exceptions.InvalidRequestException;
import com.revature.put_sets.models.SetDto;
import com.revature.put_sets.repositories.SetRepository;
import com.revature.put_sets.repositories.TagRepository;

import java.util.Map;

public class PutSetsHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

        private final TagRepository tagRepo;
        private final SetRepository setRepo;
        private static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();

        public PutSetsHandler() {
            tagRepo = new TagRepository();
            setRepo = new SetRepository();
        }

        public PutSetsHandler(TagRepository tagRepo, SetRepository setRepo) {
            this.tagRepo = tagRepo;
            this.setRepo = setRepo;
        }

        @Override
        public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {

            LambdaLogger logger = context.getLogger();
            logger.log("RECEIVED EVENT: " + requestEvent + "\n");
            APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

            try {

                Map<String, String> pathParameters = requestEvent.getPathParameters();
                String id = pathParameters.get("id");
                if (id == null) { // No id was given [400]
                    responseEvent.setStatusCode(400);
                    return responseEvent;
                }

                System.out.println("ID: " + id);

                SetDto updatedSetDto = mapper.fromJson(requestEvent.getBody(), SetDto.class);
                System.out.println("TAGS: " + updatedSetDto.getTags());
                if (!updatedSetDto.getTags().isEmpty()
                        &&
                        !tagRepo.findTags(updatedSetDto.getTags())
                                .containsAll(updatedSetDto.getTags())) {
                    throw new InvalidRequestException("One or more tags do not exist in the Tags table.");
                }


                System.out.println("UPDATED SET: " + setRepo.updateSet(id, updatedSetDto));

                return responseEvent;

            } catch (InvalidRequestException ire) {
                logger.log("ERROR [400]: " + ire.getMessage());
                responseEvent.setStatusCode(400);
                responseEvent.setBody(ire.getMessage());
                return responseEvent;
            } catch (Exception e) { // Unknown exception [500]
                logger.log("ERROR [500]: " + e.getMessage());
                responseEvent.setStatusCode(500);
                return responseEvent;
            }
        }

}
