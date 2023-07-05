package strategies;

import org.example.Helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    private Path path;

    public FileBucket() {
        try {
            path = Files.createTempFile(null, ".tmp");
            path.toFile().deleteOnExit();
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            Helper.logException(e);
        }
    }

    public long getFileSize() {
        try {
            return Files.size(path);
        } catch (IOException e) {
            Helper.logException(e);
            return 0L;
        }
    }

    public void putEntry(Entry e) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            objectOutputStream.writeObject(e);
        } catch (IOException ex) {
            Helper.logException(ex);
        }
    }

    public Entry getEntry() {
        if (getFileSize() > 0) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))) {
                return (Entry) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                Helper.logException(e);
            }
        }
        return null;
    }
}