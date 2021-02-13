import com.czareg.dto.SessionDTO;
import com.czareg.service.notblocking.BackendNotBlockingService;
import com.czareg.service.notblocking.BackendNotBlockingServiceFactory;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Before;
import org.junit.Test;

public class BackendNotBlockingServiceManualTest {
    //private static final String URL = "https://estimatefx-server.herokuapp.com";
    private static final String URL = "http://localhost";
    private BackendNotBlockingService backendNotBlockingService;

    @Before
    public void setUp() {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
        propertiesConfiguration.setFileName("test.properties");
        propertiesConfiguration.addProperty("backend.url", URL);
        //propertiesConfiguration.addProperty("proxy.enabled", true);
        //propertiesConfiguration.addProperty("proxy.host", "201.91.82.155");
        //propertiesConfiguration.addProperty("proxy.port", 3128);
        backendNotBlockingService = new BackendNotBlockingServiceFactory().create(propertiesConfiguration);
    }

    @Test
    public void pollSessionsTest() throws InterruptedException {
        Observer<SessionDTO> observer = new Observer<SessionDTO>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("sub");
            }

            @Override
            public void onNext(SessionDTO sessionDTO) {
                System.out.println(sessionDTO);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable);
            }

            @Override
            public void onComplete() {
                System.out.println("complete");
            }
        };
        backendNotBlockingService.pollSessions().subscribe(observer);
        Thread.sleep(120 * 1000L);
    }
}
