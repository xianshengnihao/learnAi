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
import dev.langchain4j.model.zhipu.ZhipuAiChatModel;
import dev.langchain4j.model.zhipu.ZhipuAiStreamingChatModel;
import dev.langchain4j.model.zhipu.chat.ChatCompletionModel;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author haoweige
 * @desc _01_ZhiPu_HelloWorld
 * @date 2024/8/12-21:43
 */
public class _01_ZhiPu_HelloWorld {
    /**
     * 淘宝购买的账号
     */
    private static final String API_KEY = "e0990fd99ebd763e7df16eb469a9d38c.iWzQ5NeDGL1RuWIS";
    private static final String BASE_URL = "https://open.bigmodel.cn/";
    static ChatLanguageModel model =  ZhipuAiChatModel.builder().apiKey(API_KEY).baseUrl(BASE_URL).build();
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

        StreamingChatLanguageModel model = ZhipuAiStreamingChatModel.builder()
                .baseUrl(BASE_URL)
                .apiKey(API_KEY)
                .model(ChatCompletionModel.GLM_3_TURBO.toString())
                .build();
        model.generate("帮我画个 越野车，什么类型都可以", new StreamingResponseHandler<AiMessage>() {
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
