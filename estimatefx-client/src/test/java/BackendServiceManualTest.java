import com.czareg.service.*;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import retrofit2.Retrofit;

@Ignore
public class BackendServiceManualTest {
    private static final String URL = "https://estimatefx-server.herokuapp.com";
    private BackendService backendService;

    @Before
    public void setUp() {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
        propertiesConfiguration.setFileName("test.properties");
        propertiesConfiguration.addProperty("backend.url", URL);
        propertiesConfiguration.addProperty("proxy.enabled", true);
        propertiesConfiguration.addProperty("proxy.host", "201.91.82.155");
        propertiesConfiguration.addProperty("proxy.port", 3128);
        Retrofit retrofit = new RetrofitClientFactory().geRetrofitClient(propertiesConfiguration);
        BackendApi backendApi = new BackendApiFactory().createBackendApi(retrofit);
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
