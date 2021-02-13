import com.czareg.service.blocking.BackendBlockingService;
import com.czareg.service.blocking.BackendBlockingServiceFactory;
import com.czareg.service.shared.BackendServiceException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Before;
import org.junit.Test;

public class BackendBlockingServiceManualTest {
    //private static final String URL = "https://estimatefx-server.herokuapp.com";
    private static final String URL = "http://localhost";
    private BackendBlockingService backendBlockingService;

    @Before
    public void setUp() {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
        propertiesConfiguration.setFileName("test.properties");
        propertiesConfiguration.addProperty("backend.url", URL);
        //propertiesConfiguration.addProperty("proxy.enabled", true);
        //propertiesConfiguration.addProperty("proxy.host", "201.91.82.155");
        //propertiesConfiguration.addProperty("proxy.port", 3128);
        backendBlockingService = new BackendBlockingServiceFactory().create(propertiesConfiguration);
    }

    @Test
    public void createSessionTest() throws BackendServiceException {
        System.out.println(backendBlockingService.createSession("John"));
    }

    @Test
    public void joinSessionTest() throws BackendServiceException {
        System.out.println(backendBlockingService.joinSession(1, "John2"));
    }

    @Test
    public void startVotingOnSessionTest() throws BackendServiceException {
        backendBlockingService.startVotingOnSession(1, "John");
    }

    @Test
    public void stopVotingOnSessionTest() throws BackendServiceException {
        backendBlockingService.stopVotingOnSession(1, "John2");
    }

    @Test
    public void getSessionsTest() throws BackendServiceException {
        System.out.println(backendBlockingService.getSessions());
    }

    @Test
    public void getSessionIdentifiersTest() throws BackendServiceException {
        System.out.println(backendBlockingService.getSessionIdentifiers());
    }

    @Test
    public void getSessionTest() throws BackendServiceException {
        System.out.println(backendBlockingService.getSession(0));
    }

    @Test
    public void leaveSessionTest() throws BackendServiceException {
        backendBlockingService.leaveSession(0, "John");
    }

    @Test
    public void voteOnSessionTest() throws BackendServiceException {
        backendBlockingService.voteOnSession(3, "John", "?");
    }
}
