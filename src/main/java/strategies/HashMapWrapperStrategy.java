package strategies;

import org.example.Helper;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class HashMapWrapperStrategy implements SimpleHashMapStrategy {

    private final HashMap <Long, String> map = new HashMap<>();
    @Override
    public void put(Long key, String value) {
        map.put(key,value);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean containsKey(Long key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(String value) {
        return map.containsValue(value);
    }

    @Override
    public String getValue(Long id) {
        return map.get(id);
    }

    @Override
    public Long getKey(String value) {
        for (Long key:
             map.keySet()) {
            if(map.get(key).equals(value)) return key;
        }
        Helper.logException(new NoSuchElementException());
        return 0L;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
