package src.ca.ucalgary.seng300;

import org.junit.Assert;

public class Utils {

    public void assertEquals(Object obj1, Object obj2, String error) {
        Assert.assertEquals(error, obj1, obj2);
    }

    public void assertEquals(int obj1, int obj2, String error) {
        Assert.assertEquals(error, obj1, obj2);
    }

    public void assertEquals(float obj1, float obj2, String error) {
        Assert.assertEquals(error, obj1, obj2);
    }

    public void assertEquals( boolean obj1, boolean obj2, String error) {
        Assert.assertEquals(error, obj1, obj2);
    }
}
