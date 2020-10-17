import com.czareg.service.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class BackendServiceManualTest {
    public static final String URL = "http://localhost:8080";
    private BackendService backendService;

    @Before
    public void setUp() {
        BackendApi backendApi = new BackendApiFactory().createBackendApi(URL);
        backendService = new BackendServiceImpl(backendApi);
    }

    @Test
    public void createSessionTest() throws BackendServiceException {
        System.out.println(backendService.createSession("John"));
    }

    @Test
    public void joinSessionTest() throws BackendServiceException {
        System.out.println(backendService.joinSession(1, "John2"));
    }

    @Test
    public void startVotingOnSessionTest() throws BackendServiceException {
        backendService.startVotingOnSession(1, "John");
    }

    @Test
    public void stopVotingOnSessionTest() throws BackendServiceException {
        backendService.stopVotingOnSession(1, "John2");
    }

    @Test
    public void getSessionsTest() throws BackendServiceException {
        System.out.println(backendService.getSessions());
    }

    @Test
    public void getSessionIdentifiersTest() throws BackendServiceException {
        System.out.println(backendService.getSessionIdentifiers());
    }

    @Test
    public void getSessionTest() throws BackendServiceException {
        System.out.println(backendService.getSession(0));
    }

    @Test
    public void leaveSessionTest() throws BackendServiceException {
        backendService.leaveSession(0, "John");
    }

    @Test
    public void voteOnSessionTest() throws BackendServiceException {
        backendService.voteOnSession(3, "John", "?");
    }
}
