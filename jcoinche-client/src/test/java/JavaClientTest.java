import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static sun.misc.PostVMInitHook.run;

public class JavaClientTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void versionTest(){
        try {
            Assume.assumeThat(System.getProperty("java.version"), CoreMatchers.startsWith("1.8"));
            run();
            System.out.println("Java version: OK");
        } catch (Exception e) {
            fail("Incorrect java version.");
        }
    }

    @Test
    public void firewallTest() {

        try {
            String ipAddress = "127.0.0.1";
            InetAddress inet = InetAddress.getByName(ipAddress);
            if (inet.isReachable(5000)){
                System.out.println("Firewall: OK");
            } else {
                fail("Firewall is blocking.");
            }
        } catch ( Exception e ) {
            fail("Firewall test failing.");
        }
    }
}
