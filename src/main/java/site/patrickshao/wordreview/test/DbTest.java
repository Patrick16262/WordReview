package site.patrickshao.wordreview.test;

import site.patrickshao.wordreview.cache.database.DataBases;

public class DbTest {
    public static void main(String[] args) throws Exception {
//        @Cleanup
//        var cache = DataBases.openCachedDict("youdao");
//        System.out.println(cache.lookupWord("frowning"));
//        DataBases.CachDict(new BaicizhanOnlineDict());
//        System.out.println(new BaicizhanOnlineDict().allwords());
        DataBases.openDataBase("data/users/1001").delete();
    }
}
