package strategies;

public interface SimpleHashMapStrategy extends Cloneable{
    void put(Long key, String value);
    int size();

    boolean containsKey(Long key);
    boolean containsValue(String value);
    String getValue(Long id);
    Long getKey (String value);
}
