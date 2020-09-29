import com.czareg.service.BackendService;
import com.czareg.service.BackendServiceCallFailedException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class BackendServiceManualTest {
    public static final String URL = "http://localhost:8080";
    private BackendService backendService;

    @Before
    public void setUp() {
        backendService = new BackendService(URL);
    }

    @Test
    public void createSessionTest() throws BackendServiceCallFailedException {
        System.out.println(backendService.createSession("John"));
    }

    @Test
    public void joinSessionTest() throws BackendServiceCallFailedException {
        System.out.println(backendService.joinSession(0, "John2"));
    }

    @Test
    public void startVotingOnSessionTest() throws BackendServiceCallFailedException {
        backendService.startVotingOnSession(0, "John");
    }

    @Test
    public void stopVotingOnSessionTest() throws BackendServiceCallFailedException {
        backendService.stopVotingOnSession(0, "John");
    }

    @Test
    public void getSessionsTest() throws BackendServiceCallFailedException {
        System.out.println(backendService.getSessions());
    }

    @Test
    public void getSessionTest() throws BackendServiceCallFailedException {
        System.out.println(backendService.getSession(0));
    }

    @Test
    public void leaveSessionTest() throws BackendServiceCallFailedException {
        backendService.leaveSession(0, "John");
    }

    @Test
    public void voteOnSessionTest() throws BackendServiceCallFailedException {
        backendService.voteOnSession(3, "John", "?");
    }
}
