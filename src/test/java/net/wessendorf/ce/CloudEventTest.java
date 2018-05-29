package net.wessendorf.ce;

import net.wessendorf.ce.beans.Receiver;
import net.wessendorf.ce.beans.Router;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class CloudEventTest extends AbstractTestBase {


    @Inject
    private Router router;

    @Deployment
    public static JavaArchive createDeployment() {
        return AbstractTestBase.createFrameworkDeployment()
                .addPackage(Router.class.getPackage());
    }

    @Test
    public void testDispatch(final Receiver receiver) {
        router.routeMe();
        Mockito.verify(receiver, Mockito.times(1)).ack();
    }
}
