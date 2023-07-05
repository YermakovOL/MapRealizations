package strategies;

import java.io.Serializable;
import java.util.Objects;

public class Entry implements Serializable {
    private final Long key;
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public void setNext(Entry next) {
        this.next = next;
    }

    private Entry next;
    private final int hash;

    public Entry getNext() {
        return next;
    }

    public Entry(int hash, Long key, String value, Entry next) {
        this.key = key;
        this.value = value;
        this.next = next;
        this.hash = hash;
    }

    public Long getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + "/" + value + "\n";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Entry entry = (Entry) obj;
        if (!Objects.equals(key, entry.key)) return false;
        return Objects.equals(value, entry.value);
    }
}
