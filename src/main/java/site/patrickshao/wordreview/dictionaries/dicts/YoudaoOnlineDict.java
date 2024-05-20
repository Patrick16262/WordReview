package site.patrickshao.wordreview.dictionaries.dicts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import site.patrickshao.wordreview.exception.DictConnFaultException;
import site.patrickshao.wordreview.exception.DictWordNotFoundException;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;

@NoArgsConstructor
public final class YoudaoOnlineDict implements OnlineDict {
    //有道云词典
    //直接通过URL请求网页，并把单词从网页上扒下来
    @Getter
    private final String id = "youdao";
    @Override

    public Word lookupWord(@NonNull String englishWord) throws DictWordNotFoundException, DictConnFaultException {
//        Word resultWord = null;
//        URLConnection conn = null;
//        try {
//            String url_base = "https://dict.youdao.com/result?word=";
//            String url_end = "&lang=en";
//            conn = new URL(url_base + URLEncoder.encode(englishWord, StandardCharsets.UTF_8) + url_end).openConnection();
//            conn.setConnectTimeout(1000);
//            try (var stream = conn.getInputStream();
//                 var reader = new BufferedReader(new InputStreamReader(stream))) {
//                StringBuilder textBuf = new StringBuilder();
//                var line = reader.readLine();
//                while (line != null) {
//                    textBuf.append(line);
//                    line = reader.readLine();
//                }
//                String text = textBuf.toString();
//                String titleRegex = "<div class=\"title\" data-.{0,20}?>(.{0,30}?)<div";
//                var matcher = Pattern.compile(titleRegex).matcher(text);
//                String str = null;
//                if (matcher.find()) {
//                    str = matcher.group(1);
//                    resultWord = new Word(str);
//                } else {
//                    throw new DictWordNotFoundException();
//                }
//                var meanings = resultWord.getMeanings();
//                String meaningRegex = "<li class=\"word-exp\" data-.{0,20}?><span class=\"pos\" data-.{0,20}?>(.{1,10}?)</span><span class=\"trans\" data-.{0,20}?>(.{1,200}?)</span></li>";
//                matcher = Pattern.compile(meaningRegex).matcher(text);
//                while (matcher.find()) {
//                    var partofspeech = matcher.group(1);
//                    str = matcher.group(2);
//                    var strs = Texts.htmlFromEscape(str).split("；");
//                    for (var c : strs) {
//                        String meaningPrefixRegex = "<(.{1,20}?)>|\\((.{1,20}?)\\)|（(.{1,20}?)）";
//                        var prefixMatcher = Pattern.compile(meaningPrefixRegex).matcher(c);
//                        String prefix = null;
//                        if (prefixMatcher.find()) {
//                            prefix = prefixMatcher.group(0);
//                            c = c.replaceAll(prefix, "");
//                        }
//                        var cs = c.split("，");
//                        resultWord.getMeanings().add(new Meaning(Texts.toEnum(partofspeech), prefix, cs, id));
//                    }
//                }
//                if (resultWord.getMeanings().isEmpty()) {
//                    String meaningRegionRegex = "<div class=\"trans-container\" data-.{1,15}?><!----><ul class=\"basic\" data-.{1,15}?><li class=\"word-exp\" data-.{1,15}?><!---->(.*?)</div>";
//                    var region = Pattern.compile(meaningRegionRegex).matcher(text);
//                    if (region.find()) {
//                        var meaningRegion = region.group(1);
//                        String meaningPhraseRegex = "<span class=\"trans\" data-.{2,15}?>(.{1,50}?)</span>";
//                        var macher = Pattern.compile(meaningPhraseRegex).matcher(meaningRegion);
//                        while (macher.find()) {
//                            var strs = Texts.htmlFromEscape(macher.group(1)).split("、");
//                            strs[0] = strs[0].replaceAll("[0-9]．", "");
//                            var meaning = new Meaning(PartOfSpeech.Phrase, null, strs, id);
//                            if (macher.find()) {
//                                try {
//                                    var str1 = macher.group(1).replaceAll("· ", "");
//                                    macher.find();
//                                    var str2 = macher.group(1);
//                                    var exp = new ExpSentence(str1, str2);
//                                    meaning.getSentences().add(exp);
//                                } catch (IllegalStateException e) {
//                                    throw new DictWordNotFoundException(englishWord + ": 正则错误！");
//                                }
//                            }
//                            resultWord.getMeanings().add(meaning);
//                        }
//                    } else {
//                        throw new DictWordNotFoundException(englishWord + ": 没有这个词");
//                    }
//                }
//            }
//        } catch (DictWordNotFoundException e) {
//            throw e;
//        } catch (SocketTimeoutException e) {
//            throw new DictConnFaultException("连接到有道词典超时");
//        } catch (IOException e) {
//            throw new DictConnFaultException(e);
//        }
        return null;
    }

}
