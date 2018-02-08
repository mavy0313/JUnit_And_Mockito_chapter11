package exercises;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FridgeRefactoredTest {

    private Fridge fridge = new Fridge();

    @Test
    public void testFridge() {
        fridge.put("cheese");
        assertEquals(true, fridge.contains("cheese"));
        assertEquals(false, fridge.put("cheese"));
        assertEquals(true, fridge.contains("cheese"));
        assertEquals(false, fridge.contains("ham"));
        fridge.put("ham");
        assertEquals(true, fridge.contains("cheese"));
        assertEquals(true, fridge.contains("ham"));
    }

    @Test
    public void shouldThrowNoSuchItemException() throws NoSuchItemException {
        catchException(fridge).take("sausage");

        assertTrue(caughtException() instanceof NoSuchItemException );
        assertEquals("sausage not found in the fridge", caughtException().getMessage());
    }

    @Test
    public void testPutTake() throws NoSuchItemException {
        List<String> food = new ArrayList<>();
        food.add("yogurt");
        food.add("milk");
        food.add("eggs");
        for (String item : food) {
            fridge.put(item);
            assertEquals(true, fridge.contains(item));
            fridge.take(item);
            assertEquals(false, fridge.contains(item));
        }
        for (String item : food) {
            try {
                fridge.take(item);
                fail("there was no " + item + " in the fridge");
            } catch (NoSuchItemException e) {
                assertEquals(true, e.getMessage().contains(item));
            }
        }
    }

}
