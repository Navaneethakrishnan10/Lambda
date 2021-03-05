package krishpack;
import Template.Template;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gradle.Details;
import gradle.Response;


/**
 * krishclass implements RequestHandler class.
 * @author navanee
 */

public class krishclass implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{

    Template service=new Template();

    /**
     * @param apiGatewayProxyRequestEvent Request
     * @param context context
     * @return Response
     */
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        String request=apiGatewayProxyRequestEvent.getBody();
        String res;
        /**
         * using object mapper, string converted into object.
         */
        APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent=new APIGatewayProxyResponseEvent();
        ObjectMapper objectMapper=new ObjectMapper();
        try
        {
            Details empDetails=objectMapper.readValue(request, Details.class);
            Response response=service.createUser(empDetails);
            res=objectMapper.writeValueAsString(response);
            apiGatewayProxyResponseEvent.setBody(res);
            return apiGatewayProxyResponseEvent;
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return apiGatewayProxyResponseEvent;
    }
}
