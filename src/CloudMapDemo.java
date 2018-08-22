import java.util.List;
import com.amazonaws.regions.Regions;
import com.ranjitha.sdks.CloudMap;
import com.ranjitha.sdks.CloudMapConfig;
import com.ranjitha.sdks.CloudMapEntry;

/**
 * A demo class that shows the basic usage of the CloudMap APIs.
 */
public class CloudMapDemo {
    public static void main(String[] args) {
        // Initialize CloudMap object.
        final String accessKey = "AKIAJ5ZFQKULXOUSRRYA";
        final String secretKey = "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";
        final String tableName = "cloudMap";
        final String hashName = "myHash";
        final String region = Regions.US_EAST_1.getName();
        CloudMapConfig config = new CloudMapConfig(hashName, accessKey, secretKey, tableName, region);
        CloudMap map = new CloudMap(config);

        // Add entries to the CloudMap.
        System.out.println("Adding entries to the cloud map");
        map.put("key1", "value1");
        map.put("key2", "value2");

        // Retrieving entries from the CloudMap.
        if (map.containsKey("key1")) {
            String value = map.get("key1");
            System.out.println("Value for key1 is: " + value);
        }

        // Removing an entry from CloudMap.
        map.remove("key1");

        // Listing all entries from a CloudMap.
        List<CloudMapEntry> entries = map.entrySet();
        System.out.println("Listing CloudMap entries...");
        for(CloudMapEntry entry : entries) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
