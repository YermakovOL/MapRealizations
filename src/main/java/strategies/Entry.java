package strategies;

import java.util.Objects;

public class Entry {
    private final Long id;
    private final String value;
    private final Entry next;
    private int hash;

    public Entry(int hash, Long id, String value, Entry next) {
        this.id = id;
        this.value = value;
        this.next = next;
        this.hash = hash;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return id + "/" + value + "\n";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id) ^ Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Entry entry = (Entry) obj;
        if (!Objects.equals(id, entry.id)) return false;
        return Objects.equals(value, entry.value);
    }
}
