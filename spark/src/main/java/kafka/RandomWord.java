package kafka;

import java.util.Random;

/**
 * Author: sheep.Old
 * WeChat: JiaWei-YANG
 * QQ: 64341393
 * Created 2018/6/19
 */
public class RandomWord {

    public static void main(String[] args) throws InterruptedException {


        while (true) {
            int assiCode = new Random().nextInt(26) + 97;

            char word = (char) assiCode;
            System.out.println("word = " + word);
            Thread.sleep(100);
        }


    }
}
