import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger nickLengthThree = new AtomicInteger(0);
    public static AtomicInteger nickLengthFour = new AtomicInteger(0);
    public static AtomicInteger nickLengthFive = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[6];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        System.out.println(Arrays.toString(texts));
        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) incrementCounter(text);
            }
        });
        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isBeautiful(text)) incrementCounter(text);
            }
        });
        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (sortNickName(text)) incrementCounter(text);
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("Красивый слов с длиной 3: " + nickLengthThree.get() + " шт.");
        System.out.println("Красивый слов с длиной 4: " + nickLengthFour.get() + " шт.");
        System.out.println("Красивый слов с длиной 5: " + nickLengthFive.get() + " шт.");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        return text.equalsIgnoreCase(new StringBuilder(text).reverse().toString());
    }

    public static boolean isBeautiful(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) != text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean sortNickName(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    private static void incrementCounter(String text) {
        switch (text.length()) {
            case 3 -> nickLengthThree.incrementAndGet();
            case 4 -> nickLengthFour.incrementAndGet();
            case 5 -> nickLengthFive.incrementAndGet();
        }
    }
}