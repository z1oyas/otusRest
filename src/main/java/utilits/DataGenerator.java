package utilits;

import java.util.Random;

public class DataGenerator {
  private static final Random random = new Random();
  private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";

  public static long generateId() {
    // Генерация id
    return 1_000_000_000L + (long)(random.nextDouble() * 8_999_999_999L);
  }

  public static String generateName(int length) {
    StringBuilder name = new StringBuilder();
    for (int i = 0; i < length; i++) {
      char letter = LETTERS.charAt(random.nextInt(LETTERS.length()));
      name.append(letter);
    }
    return name.toString();
  }
}
