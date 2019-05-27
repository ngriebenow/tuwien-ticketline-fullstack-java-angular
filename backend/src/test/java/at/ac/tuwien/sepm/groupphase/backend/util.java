package at.ac.tuwien.sepm.groupphase.backend;

import at.ac.tuwien.sepm.groupphase.backend.datagenerator.DefinedUnitDataGenerator;
import org.junit.Test;

public class util {

  @Test
  public void test() {

    int[] arr = new int[] {0, 0, 1, 1, 3, 3, 6, 7, 9, 23};

    DefinedUnitDataGenerator.normalizeArray(arr);

    System.out.println(arr.length);
  }
}
