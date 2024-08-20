package hwg.zhipuai.spring;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.time.Duration;

/**
 * @author haoweige
 * @desc EmbeddingModelProperties
 * @date 2024/8/12-22:33
 */
@Data
public class EmbeddingModelProperties {

    String baseUrl;
    String apiKey;
    String organizationId;
    String modelName;
    Integer dimensions;
    String user;
    Duration timeout;
    Integer maxRetries;
    //@NestedConfigurationProperty
    //ProxyProperties proxy;
    Boolean logRequests;
    Boolean logResponses;
}
