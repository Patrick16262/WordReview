package site.patrickshao.wordreview.dictionaries.dicts;

import lombok.Cleanup;
import lombok.NonNull;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;
import site.patrickshao.wordreview.exception.DictConnFaultException;
import site.patrickshao.wordreview.exception.DictWordNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import com.google.gson.*;

public interface OnlineDict{
    //词典类的接口
    //查找单词, 获得单词表最好在新线程进行
    //目前只包括英译中

    public Word lookupWord(@NonNull String englishWord) throws DictWordNotFoundException, DictConnFaultException;

    @NonNull
    public String getId();

    @NonNull
    public default Set<String> allwords() throws IOException {
        @Cleanup
        var reader = Files.newBufferedReader(Paths.get("data/allwordlist/default.json"));
        var list = new Gson().fromJson(reader, Set.class);
        return list;
    }
}
