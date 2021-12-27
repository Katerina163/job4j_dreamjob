package ru.job4j.dream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Config {
    private String path = System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties";
    private Map<String, String> values = new HashMap<String, String>();

    public Config() {
        load();
    }

    private void load() {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            values = in.lines()
                    .filter(a -> a.contains("="))
                    .filter(a -> !a.startsWith("#"))
                    .collect(Collectors.toMap(
                            s -> {
                                s = s.substring(0, s.indexOf("="));
                                if (s.equals("")) {
                                    throw new IllegalArgumentException();
                                }
                                return s;
                            },
                            s -> {
                                s = s.substring(s.indexOf("=") + 1);
                                if (s.equals("")) {
                                    throw new IllegalArgumentException();
                                }
                                return s;
                            }
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String value(String key) {
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }
}