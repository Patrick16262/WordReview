package site.patrickshao.wordreview.dictionaries.ai;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import site.patrickshao.wordreview.exception.AIConnectFaultException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

//由于无法境外支付，只能使用ChatGPT镜像api
public class ChatGPT3Turbo extends PromptedAI {
    @Getter
    int usage = 0;


    @Override
    public @NonNull String sendMessage(String message) throws AIConnectFaultException {
        final String key = "sk-LK9ch8aSzAml5YXwAVuGkKZ2vQFopgRK0R46KbeMFB8UqVvG";
        //原本api，但没有充值： final String key = "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";
        var client = HttpClient.newHttpClient();
        //镜像api https://api.chatanywhere.com.cn
        var req = new Request();
        req.getMessages()[0] = new Request2(message);
        var requst = HttpRequest.newBuilder(URI.create("https://api.chatanywhere.com.cn/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + key)
                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(req, Request.class))).build();
        try {
            var response = client.send(requst, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() != 200) {
                throw new AIConnectFaultException(response.body());
            }
            var rece = new Gson().fromJson(response.body(), Receive.class);
            usage = rece.getUsage().getTotal_tokens();
            return rece.getChoices()[0].getMessage().getContent();
        } catch (IOException | InterruptedException e) {
            throw new AIConnectFaultException(e);
        }
    }

    @Data
    class Request {
        private final String model = "gpt-3.5-turbo";
        private final Request2[] messages = new Request2[1];
    }

    @Data
    static
    class Request2 {
        private final String role = "user";
        private final String content;
    }

    //{示例代码
//        "model": "gpt-3.5-turbo",
//        "messages": [{"role": "user", "content": "Hello!"}]
//        }
    @Data
    class Receive {
        private Receive2[] choices;
        private Usage usage;
    }

    @Data
    class Receive2 {
        private Receive3 message;
    }

    @Data
    static
    class Receive3 {
        private String content;

    }
    @Data
    static
    class Usage {
        private int total_tokens;
    }
// 响应代码示例
//{
//        "id": "chatcmpl-123",
//        "object": "chat.completion",
//        "created": 1677652288,
//        "choices": [
//        {
//        "index": 0,
//        "message": {
//        "role": "assistant",
//        "content": "\n\nHello there, how may I assist you today?"
//        },
//        "finish_reason": "stop"
//        }
//        ],
//        "usage": {
//        "prompt_tokens": 9,
//        "completion_tokens": 12,
//        "total_tokens": 21
//        }
//        }
}


