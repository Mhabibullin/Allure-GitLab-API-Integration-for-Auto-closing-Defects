import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTest {

    @Test(description = "This is just a sample for run")
    public void initialTest(){
        String expectedTitle = "Title";
        String originalTitle = "Title";
        Assert.assertEquals(originalTitle, expectedTitle);
    }
}
