package com.hwg;

import cn.hutool.core.thread.ThreadUtil;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.output.Response;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * 大都督周瑜（微信: dadudu6789）
 */
public class _01_OpenAi_HelloWorld {
    /**
     * 我自己的账号
     */
    //private static final String API_KEY = "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";

    /**
     * 淘宝购买的账号
     */
    private static final String API_KEY = "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";
    private static final String BASE_URL = "https://api.openai.com/v1";

    static ChatLanguageModel  model =  OpenAiChatModel.builder().apiKey(API_KEY).baseUrl(BASE_URL).build();
    //static ChatLanguageModel  model =  OpenAiChatModel.builder().apiKey("demo").build();

    @Test
    public void test(){
        System.out.println(model.generate("你好，我是郝维格"));
        System.out.println("-------");
        System.out.println(model.generate("请重复！"));
    }

    /**
     * 上下文对话测试
     */
    @Test
    public void test2(){
        UserMessage userMessage = UserMessage.userMessage("你好，我是郝维格");
        Response<AiMessage> response = model.generate(userMessage);
        // 大模型的第一次响应
        AiMessage aiMessage1 = response.content();
        System.out.println(aiMessage1.text());
        System.out.println("----");
        // 下面一行代码是重点
        Response<AiMessage> response2 = model.generate(userMessage, aiMessage1, UserMessage.userMessage("我叫什么"));
        // 大模型的第二次响应
        AiMessage aiMessage2 = response2.content();
        System.out.println(aiMessage2.text());
    }

    /**
     * 打字机流式响应
     */
    @Test
    public void test3(){

        StreamingChatLanguageModel model = OpenAiStreamingChatModel.builder()
                .baseUrl(BASE_URL)
                .apiKey(API_KEY)
                .modelName(OpenAiChatModelName.GPT_3_5_TURBO)
                .build();
        model.generate("你好，我是郝维格", new StreamingResponseHandler<AiMessage>() {
            @Override
            public void onNext(String token) {
                System.out.println(token);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            @Override
            public void onError(Throwable error) {
                System.out.println(error);
            }
        });

        ThreadUtil.sleep(100000);

    }
}
