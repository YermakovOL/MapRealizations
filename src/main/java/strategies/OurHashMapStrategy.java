package strategies;

import java.util.Objects;

public class OurHashMapStrategy implements SimpleHashMapStrategy {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    int size;
    int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);

    final Entry getEntry(Long key) {
        if (size == 0) {
            return null;
        }
        int index = indexByHash(key, table.length);
        for (Entry entry = table[index]; entry != null; entry = entry.getNext()) {
            if (key.equals(entry.getKey())) {
                return entry;
            }
        }
        return null;

    }

    private int hash(Long key) {
        return Objects.hashCode(key);
    }

    private int indexByHash(Long key, int length) {
        return key.hashCode() & (length - 1);
    }

    @Override
    public void put(Long key, String value) {
        int index = indexByHash(key, table.length);
        if (size != 0) {
            for (Entry entry = table[index]; entry != null; entry = entry.getNext()) {
                if (entry.getKey().equals(key)) {
                    entry.setValue(value);
                    return;
                }
            }
        }
        addEntry(index, key, value);
    }

    private void addEntry(int index, Long key, String value) {
        if (size >= threshold) {
            table = transformTable(2 * table.length);
            index = indexByHash(key, table.length);
        }
        createEntry(index, key, value);
    }

    private void createEntry(int index, Long key, String value) {
        size++;
        Entry lastEntry = table[index];
        table[index] = new Entry(hash(key), key, value, lastEntry);
    }

    private Entry[] transformTable(int length) {
        Entry[] biggerTable = new Entry[length];
        for (Entry e : table) {
            while (e != null) {
                Entry next = e.getNext();
                int indexInNewTable = indexByHash(e.getKey(), biggerTable.length);
                e.setNext(biggerTable[indexInNewTable]);
                biggerTable[indexInNewTable] = e;
                e = next;
            }
        }
        threshold = (int) (length * DEFAULT_LOAD_FACTOR);
        return biggerTable;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        for (Entry e : table) {
            for (Entry entry = e; entry != null; entry = entry.getNext()) {
                if (e.getValue().equals(value)) return true;
            }
        }
        return false;
    }

    @Override
    public String getValue(Long key) {

        Entry entry = getEntry(key);
        if (entry != null)
            return entry.getValue();

        return null;
    }

    @Override
    public Long getKey(String value) {
        for (Entry e : table) {
            for (Entry entry = e; entry != null; entry = entry.getNext()) {
                if (e.getValue().equals(value)) return e.getKey();
            }
        }
        return null;
    }
}
