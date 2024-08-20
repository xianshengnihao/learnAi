package com.hwg.openai.controller;

import dev.langchain4j.data.image.Image;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.moderation.Moderation;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiImageModel;
import dev.langchain4j.model.openai.OpenAiModerationModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.output.Response;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author haoweige
 * @desc OpenAiController
 * @date 2024/8/12-23:15
 */
@RestController
@CrossOrigin("*")
@Slf4j
public class OpenAiController {

    @Resource
    private OpenAiChatModel openAiChatModel;
    @Resource
    private OpenAiStreamingChatModel openAiStreamingChatModel;
    @Resource
    private ChatLanguageModel chatLanguageModel;
    @Resource
    private OpenAiModerationModel openAiModerationModel;
    @Resource
    private OpenAiImageModel openAiImageModel;

    @GetMapping("/hello")
    public String hello(String text) {

        String generate = openAiChatModel.generate("hello");
        return generate;
    }

    /**
     * 流式处理模型
     * @param text
     */
    @GetMapping("/helloStream")
    public void helloStream(String text) {
        openAiStreamingChatModel.generate(text, new StreamingResponseHandler<AiMessage>() {
            @Override
            public void onNext(String token) {
                log.info("Received token: {}", token);
            }

            @Override
            public void onError(Throwable error) {
                // 处理错误，例如可以选择记录日志或停止输出
                System.err.println("Error in streaming: " + error.getMessage());
            }
        });
    }

    @GetMapping("/helloLanguage")
    public String helloLanguage(String text) {
        String generate = chatLanguageModel.generate(text);
        return generate;
    }

    /**
     * 敏感词模型，是敏感词会返回敏感词，不是则归回 null
     *
     * @param text
     * @return
     */
    @GetMapping("/helloModeration")
    public String helloModeration(String text) {
        Response<Moderation> moderate = openAiModerationModel.moderate(text);
        return moderate.content().flaggedText();
    }

    /**
     * 图片处理模型
     * @param text
     * @return
     */
    @GetMapping("/helloImage")
    public String helloImage(String text) {
        Response<Image> generate = openAiImageModel.generate(text);
        System.out.println(generate.content().url().toString());
        return generate.content().url().toString();
    }

}
