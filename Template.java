package Template;



import com.amazonaws.services.secretsmanager.model.DecryptionFailureException;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InternalServiceErrorException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import dynamo.Repository;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import gradle.Details;
import gradle.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Base64;


/**
 * class "Template" created.
 * @author navanee
 */
public class Template {
    RestTemplate restTemplate=new RestTemplate();

    /**
     * @param template Getting details from pojo class.
     * @return value
     */
    public Response createUser(Details template)
    {
        Repository repo=new Repository();
        String url=repo.getDetails("url");
        System.out.println("Entering secrets manager");
        getSecret();;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<Details> entity = new HttpEntity<Details>(template, headers);
        ResponseEntity<Response> responseEntity =  restTemplate
                .exchange(url, HttpMethod.POST, entity, Response.class);
        return responseEntity.getBody();
    }

    /**
     * This metod used to retrive given details from secrete manager.
     */
    public static void getSecret() {

        String secretName = "Krishna";
        String region = "us-east-2";

        AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();


        String secret, decodedBinarySecret;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException | InternalServiceErrorException | InvalidParameterException | InvalidRequestException | ResourceNotFoundException e) {
            throw e;
        }

        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
            System.out.println(secret);
        }
        else {
            decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
            System.out.println(decodedBinarySecret);
        }

    }
}
