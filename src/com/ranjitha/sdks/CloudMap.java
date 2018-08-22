package com.ranjitha.sdks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

/**
 * A DynamoDB based simplified implementation of Java's HashMap.
 */
public class CloudMap {
    private final String hashName;
    private final DynamoDBMapper dynamoDBMapper;

    /**
     * Constructor to initialize a Cloud Map with a config.
     * @param config - Cloud Map Config that is used to initialize the DynamoDB Mapper.
     */
    public CloudMap(CloudMapConfig config) {
        this.hashName = config.getHashName();

        // Setup Mapper
        BasicAWSCredentials credentials = new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey());
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(config.getAwsRegion())
                .build();
        this.dynamoDBMapper = new DynamoDBMapper(dynamoDB);
    }

    /**
     * Puts the key:value pair in the CloudMap (ie, in DynamoDB table in the backend)
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        CloudMapEntry entity = new CloudMapEntry(hashName, key, value);
        dynamoDBMapper.save(entity);
    }

    /**
     * Retrieves the value for the provided key from the CloudMap (ie, in DynamoDB table in the backend)
     * @param key
     * @return value
     */
    public String get(String key) {
        CloudMapEntry entity = dynamoDBMapper.load(CloudMapEntry.class, hashName, key);
        if (entity != null) {
            return entity.getValue();
        } else {
            return null;
        }
    }

    /**
     * Returns true if Cloud Map contains the specified key.
     * @param key
     * @return true if cloud map contains the key
     */
    public boolean containsKey(String key) {
        return get(key) != null;
    }

    /**
     * Removes the specified key from the Cloud Map.
     * @param key
     */
    public void remove(String key) {
        CloudMapEntry entry = new CloudMapEntry();
        entry.setHashName(hashName);
        entry.setKey(key);
        dynamoDBMapper.delete(entry);
    }

    /**
     * Returns all the key:value pairs for the Cloud Map.
     * @return a list of cloud map entries
     */
    public List<CloudMapEntry> entrySet() {
        Map<String, AttributeValue> attributeMap = new HashMap<>();
        attributeMap.put(":v1", new AttributeValue().withS(hashName));
        DynamoDBQueryExpression<CloudMapEntry> queryExpression = new DynamoDBQueryExpression<CloudMapEntry>()
                                                                    .withKeyConditionExpression("hashName = :v1")
                                                                    .withExpressionAttributeValues(attributeMap);
        List<CloudMapEntry> entries = dynamoDBMapper.query(CloudMapEntry.class, queryExpression);
        return entries;
    }
}
