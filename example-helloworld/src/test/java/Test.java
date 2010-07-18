import org.apache.jetty.service.JettyServiceImpl;
import org.apache.jetty.service.example.HelloWorld;

public class Test {
	public static void main(String[] args) throws Exception {
		JettyServiceImpl a = new JettyServiceImpl();
		a.init();
		
		HelloWorld h = new HelloWorld();
		h.setJettyService(a);
		h.init();
		Thread.sleep(100000);
		h.destroy();
		a.destroy();
	}
}