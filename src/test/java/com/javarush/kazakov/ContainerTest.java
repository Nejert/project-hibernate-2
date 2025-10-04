package com.javarush.kazakov;

import com.javarush.kazakov.config.Container;
import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContainerTest extends Container {
    @Test
    public void testContainer() {
        String databaseName = "movie";
        String username = "root";
        String password = "root";

        Map<String, Object> properties = SESSION_FACTORY.getProperties();
        String url = properties.get(Environment.JAKARTA_JDBC_URL).toString();
        Matcher matcher = Pattern.compile("jdbc:p6spy:mysql://localhost:\\d+/movie").matcher(url);

        Assertions.assertEquals(databaseName, CONTAINER.getDatabaseName());
        Assertions.assertEquals(username, CONTAINER.getUsername());
        Assertions.assertEquals(password, CONTAINER.getPassword());
        Assertions.assertTrue(matcher.matches());
    }
}
