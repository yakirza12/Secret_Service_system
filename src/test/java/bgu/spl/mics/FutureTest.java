package bgu.spl.mics;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class FutureTest {
    Future<Object> f;

    @BeforeEach
    public void setUp() {
        f = new Future<Object>();
    }

    @Test
    public void testGet() {
        String myResolve = "my result";
        f.resolve(myResolve);
        Object result = f.get();
        assertEquals(result, myResolve);
    }


    @Test
    public void testResolve() {
        String myResolve = "my result";
        f.resolve(myResolve);
        assertEquals(true, f.isDone());
        Object result = f.get();
        assertEquals(result, myResolve);

    }


    @Test
    public void isDone() {
        assertEquals(false, f.isDone());
        String myResolve = "my result";
        f.resolve(myResolve);
        assertEquals(true, f.isDone());
    }


    @Test
    public void testGetWithTime() {
        Object result = f.get(1000,TimeUnit.MILLISECONDS);
        assertNull(result);
        String myResolve = "my result";
        f.resolve(myResolve);
        Object result2 = f.get(1000,TimeUnit.MILLISECONDS);
        assertEquals(result2, myResolve);

        String myResolve1 = "my result1";
        f.resolve(myResolve1);
        Object result3 = f.get(-1,TimeUnit.MILLISECONDS);// Check if needed
        assertNull(result3);


    }

}