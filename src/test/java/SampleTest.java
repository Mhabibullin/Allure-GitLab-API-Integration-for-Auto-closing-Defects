import mkhabibullin.listeners.AllureGitlabListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(AllureGitlabListener.class)
public class SampleTest {

    @Test(description = "This is just a sample for run")
    public void initialTest() {
        String expectedTitle = "Title";
        String originalTitle = "Title";
        Assert.assertEquals(originalTitle, expectedTitle);
    }
}
