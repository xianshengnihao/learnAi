package hwg.zhipuai.spring;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.time.Duration;

/**
 * @author haoweige
 * @desc LanguageModelProperties
 * @date 2024/8/12-22:26
 */
@Data
public class LanguageModelProperties {

    String baseUrl;
    String apiKey;
    String organizationId;
    String modelName;
    Double temperature;
    Duration timeout;
    Integer maxRetries;
    //@NestedConfigurationProperty
    //ProxyProperties proxy;
    Boolean logRequests;
    Boolean logResponses;
}
