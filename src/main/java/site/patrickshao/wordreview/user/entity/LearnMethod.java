package site.patrickshao.wordreview.user.entity;

public enum LearnMethod implements ReciteMethod {
    Dictation, //听写
    WriteMeaning, //根据英文写中文
    WriteEnglish, //根据中文写英文
    Read, //没有测试题, 给出单词(可选:释义)选出自己是否熟悉
    Selection_Trans, //根据单词选意思
    Selection_En, //根据意思选单词


}
