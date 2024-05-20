package site.patrickshao.wordreview.dictionaries.ai;

import lombok.NonNull;
import site.patrickshao.wordreview.exception.AIConnectFaultException;


//问答式AI接口
public interface Questionable {
    @NonNull String sendMessage(String Message) throws AIConnectFaultException;

    int getUsage();
}
