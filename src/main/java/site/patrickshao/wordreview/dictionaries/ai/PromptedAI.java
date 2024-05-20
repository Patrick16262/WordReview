package site.patrickshao.wordreview.dictionaries.ai;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import site.patrickshao.wordreview.dictionaries.entity.basic.ExpSentence;
import site.patrickshao.wordreview.dictionaries.entity.basic.Word;
import site.patrickshao.wordreview.exception.AIConnectFaultException;
import site.patrickshao.wordreview.exception.AIRespondErrorErrorException;

public abstract class PromptedAI implements Questionable {
    @Getter
    @Setter
    private int choices = 2;


    public void sentenceMeaningMatch(@NonNull Word word, @NonNull ExpSentence expSentence) throws AIConnectFaultException, AIRespondErrorErrorException {
//        if (word.getMeanings().size() <= 1) {
//            word.getMeanings().get(0).getSentences().add(expSentence);
//            return;
//        } else {
//            for (int k = 0; k < choices; k++) {
//                StringBuilder prompt = new StringBuilder(expSentence.getEn());
//                prompt.append("\n该例句包含'");
//                prompt.append(word.getName_en());
//                prompt.append("'的哪个意思\n");
//                for (int i = 0; i < word.getMeanings().size(); i++) {
//                    prompt.append(i);
//                    prompt.append(". ");
//                    prompt.append(Arrays.toString(word.getMeanings().get(i).getCh_names()));
//                    prompt.append('\n');
//                }
//                prompt.append("请回复与之匹配的序号，并把它包含到一个的json对象中，这个对象只有一个名为'answer'的int类型字段，不要废话");
//                var res = this.sendMessage(prompt.toString());
//                res = Texts.deleteWrapping(res);
//                System.out.println(res);
//                var matcher = Pattern.compile("\\{\"answer\": ([0-9]{1,2})\\}").matcher(res);
//
//                if (matcher.find()) {
//                    try {
//                        System.out.println(matcher.group(1));
//                        int t = Integer.parseInt(matcher.group(1));
//                        word.getMeanings().get(t).getSentences().add(expSentence);
//                        return;
//                    } catch (Exception e) {
//
//                    }
//                }
//            }
//            throw new AIRespondErrorErrorException();
//        }
    }

}
