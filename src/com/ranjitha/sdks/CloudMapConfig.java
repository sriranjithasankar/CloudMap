package com.ranjitha.sdks;

import lombok.Data;
import lombok.AllArgsConstructor;

/**
 * Class that contains the configuration parameters for the initialization of DynamoDB Mapper.
 */
@Data
@AllArgsConstructor
public class CloudMapConfig {
    private String hashName;
    private String accessKey;
    private String secretKey;
    private String tableName;
    private String awsRegion;
}
