import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger cnt3 = new AtomicInteger(0);
    public static AtomicInteger cnt4 = new AtomicInteger(0);
    public static AtomicInteger cnt5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        //сгенерированное слово является палиндромом, т. е. читается одинаково как слева направо, так и справа налево, например, abba;
        new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (isPalindrome(texts[i])) {
                    if (texts[i].length() == 3) cnt3.getAndIncrement();
                    if (texts[i].length() == 4) cnt4.getAndIncrement();
                    if (texts[i].length() == 4) cnt5.getAndIncrement();
                }
            }
        }).start();

        //сгенерированное слово состоит из одной и той же буквы, например, aaa;
        new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (isRepeated(texts[i])) {
                    if (texts[i].length() == 3) cnt3.getAndIncrement();
                    if (texts[i].length() == 4) cnt4.getAndIncrement();
                    if (texts[i].length() == 4) cnt5.getAndIncrement();
                }
            }
        }).start();

        //буквы в слове идут по возрастанию: сначала все a (при наличии), затем все b (при наличии), затем все c и т. д. Например, aaccc.
        new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (isIncrease(texts[i])) {
                    if (texts[i].length() == 3) cnt3.getAndIncrement();
                    if (texts[i].length() == 4) cnt4.getAndIncrement();
                    if (texts[i].length() == 4) cnt5.getAndIncrement();
                }
            }
        }).start();

        System.out.printf("Красивых слов с длиной 3: %s шт\n", cnt3.get());
        System.out.printf("Красивых слов с длиной 4: %s шт\n", cnt4.get());
        System.out.printf("Красивых слов с длиной 5: %s шт", cnt5.get());

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String word) {
        int length = word.length();
        for (int i = 0; i < (length / 2); i++) {
            if (word.charAt(i) != word.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isRepeated(String word) {
        char c = word.charAt(0);
        for (int i = 1; i < word.length(); i++) {
            if (c != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIncrease(String word) {
        char c = word.charAt(0);
        for (int i = 0; i < word.length() - 1; i++) {
            if (word.charAt(i) > word.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }
}
