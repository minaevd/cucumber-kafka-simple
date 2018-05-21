package info.minaevd;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils
{
    private Utils()
    {
    }

    public static Properties getConfigProperties()
    {
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {

            // load a properties file
            properties.load(input);

        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return properties;
    }
}
