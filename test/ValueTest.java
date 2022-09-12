import cs3500.freecell.model.hw02.Value;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testing class for Value.
 */
public class ValueTest {

  @Test
  public void testToString() {
    assertEquals("1", Value.ACE.toString());
    assertEquals("11", Value.JACK.toString());
    assertEquals("5", Value.FIVE.toString());
  }

  @Test
  public void testReturnInt() {
    assertEquals(1, Value.ACE.returnInt());
    assertEquals(11, Value.JACK.returnInt());
    assertEquals(5, Value.FIVE.returnInt());
  }

}
