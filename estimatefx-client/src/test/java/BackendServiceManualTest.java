import com.czareg.service.BackendService;
import com.czareg.service.BackendServiceCallFailed;
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
    public void createSessionTest() throws BackendServiceCallFailed {
        System.out.println(backendService.createSession("John"));
    }

    @Test
    public void joinSessionTest() throws BackendServiceCallFailed {
        System.out.println(backendService.joinSession(0, "John2"));
    }

    @Test
    public void startVotingOnSessionTest() throws BackendServiceCallFailed {
        backendService.startVotingOnSession(0, "John");
    }

    @Test
    public void stopVotingOnSessionTest() throws BackendServiceCallFailed {
        backendService.stopVotingOnSession(0, "John");
    }

    @Test
    public void getSessionsTest() throws BackendServiceCallFailed {
        System.out.println(backendService.getSessions());
    }

    @Test
    public void getSessionTest() throws BackendServiceCallFailed {
        System.out.println(backendService.getSession(0));
    }

    @Test
    public void leaveSessionTest() throws BackendServiceCallFailed {
        backendService.leaveSession(0, "John");
    }

    @Test
    public void voteOnSessionTest() throws BackendServiceCallFailed {
        backendService.voteOnSession(3, "John", "?");
    }
}
