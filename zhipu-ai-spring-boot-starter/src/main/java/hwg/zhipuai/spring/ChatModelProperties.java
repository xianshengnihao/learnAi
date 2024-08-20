package hwg.zhipuai.spring;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * @author haoweige
 * @desc ChatModelProperties
 * @date 2024/8/12-22:25
 */
@Data
public class ChatModelProperties {
    String baseUrl;
    String apiKey;
    String organizationId;
    String modelName;
    Double temperature;
    Double topP;
    List<String> stop;
    Integer maxTokens;
    Double presencePenalty;
    Double frequencyPenalty;
    Map<String, Integer> logitBias;
    String responseFormat;
    Integer seed;
    String user;
    Duration timeout;
    Integer maxRetries;
    //@NestedConfigurationProperty
    //ProxyProperties proxy;
    Boolean logRequests;
    Boolean logResponses;
}
