package dynamo;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *Dynamodb string values were given in this class.
 * @author navanee
 */
public class Repository {
    private static final Logger LOGGER = LoggerFactory.getLogger(Repository.class);
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    DynamoDB dynamoDb = new DynamoDB(client);
    Map<String,String> map=new HashMap<String, String>();


    /**
     * @param key Used to give the string Value.
     * @return Given Value.
     */
    public String getDetails(String key) {
        System.out.println("entering dynamodb method");
        Table table = dynamoDb.getTable("LambdaDynamo");
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("Key", key);
        try {
            System.out.println("Attempting to read item");
            Item outcome = table.getItem(spec);
            if (Objects.nonNull(outcome)) {
                map.put(outcome.get("Key").toString(), outcome.get("resource").toString());

                System.out.println(map.get(key));
                return map.get(key);
            }
        } catch (RuntimeException e) {
            LOGGER.error("Exception occurred during getUserDetails : ", e);
        }
        return null;
    }}
