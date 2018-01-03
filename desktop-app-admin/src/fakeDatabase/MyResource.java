package fakeDatabase;

import org.glassfish.jersey.server.ResourceConfig;
import org.jetbrains.annotations.Contract;

import javax.ws.rs.ApplicationPath;

/**
 * Created by Максим on 03.01.2018.
 */
@ApplicationPath("/")
public class MyResource extends ResourceConfig {
    MyResource(){
        packages("fakeDatabase");
    }
}
