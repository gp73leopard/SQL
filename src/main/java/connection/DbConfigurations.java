package connection;
import org.aeonbits.owner.Config;
@Config.Sources
        ({
                "classpath:db.properties"
        })
public interface DbConfigurations extends Config{
    @Key("db.url")
    String dbUrl();
    @Key("db.user")
    String dbUser();
    @Key("db.pass")
    String dbPass();
}
