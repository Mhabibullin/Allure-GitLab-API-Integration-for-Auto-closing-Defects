import mkhabibullin.listeners.AllureGitlabListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(AllureGitlabListener.class)
public class SampleTest {

    @Test(description = "Run me to check functionality")
    public void initialTest() {
        Assert.assertTrue(true);
    }
}
