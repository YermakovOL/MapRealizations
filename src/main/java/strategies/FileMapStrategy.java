package strategies;

import java.util.Objects;

public class FileMapStrategy implements SimpleHashMapStrategy{
    private FileBucket[] table = new FileBucket[16];
    private int size = 0;
    private static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    private long maxBucketSize;
    public FileMapStrategy() {
        init();
    }

    private void init() {
        for (int i = 0; i < table.length; i++) {
            table[i]=new FileBucket();
        }
    }

    final Entry getEntry(Long key) {
        if (size == 0) {
            return null;
        }
        int index = indexByHash(key, table.length);
        for (Entry entry = table[index].getEntry(); entry != null; entry = entry.getNext()) {
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
            for (Entry entry = table[index].getEntry(); entry != null; entry = entry.getNext()) {
                if (entry.getKey().equals(key)) {
                    entry.setValue(value);
                    return;
                }
            }
        }
        addEntry(index, key, value);
    }

    private void addEntry(int index, Long key, String value) {
        if (maxBucketSize>=bucketSizeLimit) {
            table = transformTable(2 * table.length);
            index = indexByHash(key, table.length);
        }
        createEntry(index, key, value);
    }

    private void createEntry(int index, Long key, String value) {
        size++;
        Entry lastEntry = table[index].getEntry();
        table[index].putEntry(new Entry(hash(key), key, value, lastEntry));


        long currentBucketSize = table[index].getFileSize();
        if (currentBucketSize > maxBucketSize)
            maxBucketSize = currentBucketSize;
    }

    private FileBucket[] transformTable(int length) {
        maxBucketSize = 0;
        FileBucket[] biggerTable = new FileBucket[length];
        for (FileBucket fileBucket : table) {

               Entry e = fileBucket.getEntry();
               while (e != null) {
                   Entry next = e.getNext();
                   int indexInNewTable = indexByHash(e.getKey(), biggerTable.length);
                   e.setNext(biggerTable[indexInNewTable].getEntry());
                   biggerTable[indexInNewTable].putEntry(e);
                   e = next;
               }
               long currentBucketSize = fileBucket.getFileSize();
               if (currentBucketSize > maxBucketSize)
                   maxBucketSize = currentBucketSize;

        }

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
        for (FileBucket f : table) {

                Entry e = f.getEntry();
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
        for (FileBucket f : table) {

                Entry e = f.getEntry();
                for (Entry entry = e; entry != null; entry = entry.getNext()) {
                    if (e.getValue().equals(value)) return e.getKey();

            }
        }
        return null;
    }


}
