package bookmarks;

import java.util.Arrays;

/**
 * Created by w00228970 on 2017/3/31.
 */
public class WF {
    public static void main(String[] args) {
        Arrays.asList(
                "jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
                .forEach(
                        a -> {
                            String s = a + "haha";
                            System.out.println(s);
                        });
        System.out.println();
    }
}
