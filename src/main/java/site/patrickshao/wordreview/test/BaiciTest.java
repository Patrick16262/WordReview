package site.patrickshao.wordreview.test;

import site.patrickshao.wordreview.dictionaries.dicts.BaicizhanOnlineDict;

import java.io.IOException;

public class BaiciTest {
    public static void main(String[] args) throws IOException {
        System.out.println(new BaicizhanOnlineDict().lookupWord("suck"));
    }
}
