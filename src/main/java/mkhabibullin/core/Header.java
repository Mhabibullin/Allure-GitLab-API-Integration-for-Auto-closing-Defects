package mkhabibullin.core;

import java.util.LinkedHashMap;
import java.util.Map;

public class Header {
    Map<String, String> header;

    public Header() {
        this.header = new LinkedHashMap<>();
    }

    public void append(String key, String value) {
        header.put(key, value);
    }

    public void remove(String key) {
        header.remove(key);
    }

    public Map<String, String> getHeader() {
        return header;
    }
}
