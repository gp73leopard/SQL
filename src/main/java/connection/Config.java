package connection;

import org.aeonbits.owner.ConfigFactory;

public class Config {
    private static DbConfigurations dBConfigurations;
    public static DbConfigurations getDbConfigurations() {
        if (dBConfigurations == null){
            dBConfigurations = ConfigFactory.create(DbConfigurations.class);
        }
        return dBConfigurations;
    }
}
