package com.ranjitha.sdks;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * This class represents a single entry in the CloudMap (ie, a row in the DynamoDB table in the backend)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "cloudMap")
public class CloudMapEntry {
    private String hashName;
    private String key;
    private String value;

    @DynamoDBHashKey
    public String getHashName() {
        return hashName;
    }

    @DynamoDBRangeKey
    public String getKey() {
        return key;
    }

    @DynamoDBAttribute(attributeName = "value")
    public String getValue() {
        return value;
    }
}
