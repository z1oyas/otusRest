package utilits;

import java.util.Random;

public class DataGenerator {
  private static final Random RANDOM_VAL = new Random();
  private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";

  public static long generateId() {
    // Генерация id
    return 1_000_000_000L + (long)(RANDOM_VAL.nextDouble() * 8_999_999_999L);
  }

  public static String generateName(int length) {
    StringBuilder name = new StringBuilder();
    for (int i = 0; i < length; i++) {
      char letter = LETTERS.charAt(RANDOM_VAL.nextInt(LETTERS.length()));
      name.append(letter);
    }
    return name.toString();
  }
}
