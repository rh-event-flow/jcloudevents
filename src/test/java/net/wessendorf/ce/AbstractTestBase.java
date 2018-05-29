package net.wessendorf.ce;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public abstract class AbstractTestBase {

    public static JavaArchive createFrameworkDeployment() {

        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CloudEvent.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
}