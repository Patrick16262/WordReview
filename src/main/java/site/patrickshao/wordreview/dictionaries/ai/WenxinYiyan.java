package site.patrickshao.wordreview.dictionaries.ai;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import site.patrickshao.wordreview.exception.AIConnectFaultException;
import okhttp3.*;
import org.json.JSONException;

import java.io.*;


public final class WenxinYiyan extends PromptedAI {
    @Getter
    int usage = 0;
    private final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
    private Response response = null;
    @Getter
    private final String id = "wenxinyiyan";

    @NonNull
    public String sendMessage(String content) throws AIConnectFaultException {
        try {
            if (response != null) {
                response.close();
            }
            content = new Gson().toJson(content);
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"messages\":[{\"role\":\"user\",\"content\":" + content + "}]}");
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=" + getAccessToken())
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            response = HTTP_CLIENT.newCall(request).execute();
            var jsobj = new Gson().fromJson(response.body().string(), AIResult.class);
            usage += jsobj.getUsage().getTotal_tokens();
            return jsobj.getResult();
        } catch (Exception e) {
            throw new AIConnectFaultException(e);
        }
    }


    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    private String getAccessToken() throws IOException, JSONException {
        if (response != null) {
            response.close();
        }
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String SECRET_KEY = "lAT8ovrPWqDZLvWyZH5iy2HVIDGbzR6E";
        //文心一言的api,来自百度官网
        String API_KEY = "cgiN3PNudAgtVgbnmbWaOCI1";
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        response = HTTP_CLIENT.newCall(request).execute();
        return new org.json.JSONObject(response.body().string()).getString("access_token");
    }

    @Data
    static
    class AIUsage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
    }

    @Data
    static
    class AIResult {
        private final String result;
        private final String is_truncated;
        private final AIUsage usage;
    }
}
