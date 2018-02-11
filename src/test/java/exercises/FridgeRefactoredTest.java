package exercises;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class FridgeRefactoredTest {

    public static final String SAUSAGE = "sausage";
    public static final String CHEESE = "cheese";
    public static final String HAM = "ham";

    private Fridge fridge = new Fridge();

    private Object[] putItems() {
        return $($(CHEESE), $(HAM), $(SAUSAGE));
    }

    @Test
    @Parameters(method = "putItems")
    public void shouldContainPutItem(String item) {
        fridge.put(item);

        assertEquals(true, fridge.contains(item));
    }

    @Test
    public void shouldNotContainNotPutItem() {
        fridge.put(CHEESE);

        assertEquals(false, fridge.contains(HAM));
    }

    @Test
    public void shouldNotPutSameItemTwice() {
        fridge.put(CHEESE);

        assertEquals(false, fridge.put(CHEESE));
    }

    @Test
    public void shouldThrowNoSuchItemExceptionWhenTakeNotPutItem() throws NoSuchItemException {
        catchException(fridge).take(SAUSAGE);

        assertTrue(caughtException() instanceof NoSuchItemException );
        assertEquals(SAUSAGE + " not found in the fridge", caughtException().getMessage());
    }

    @Test
    @Parameters(method = "putItems")
    public void shouldNotContainTakenItems(String item) throws NoSuchItemException {
        fridge.put(item);
        fridge.take(item);

        assertEquals(false, fridge.contains(item));
    }

}
