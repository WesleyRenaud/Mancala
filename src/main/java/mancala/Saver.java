package mancala;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A class used to save and load objects from files.
 */
public class Saver implements Serializable {
    private static final long serialVersionUID = -2804233256934305899L;

    /**
     * Saves an object to a file.
     * 
     * @param toSave    The object to save.
     * @param filename  The name of the file to write to.
     * @throws IOException  If the object cannot be saved.
     */
    public void saveObject(final Serializable toSave, final String filename) throws IOException {
        // make an output stream for the file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            // write the object
            out.writeObject(toSave);
        }
    }

    /**
     * Loads an object from a file.
     * 
     * @param filename  The file to read the object from.
     * @return  The object that we read.
     * @throws IOException  If the object cannot be read.
     */
    public Serializable loadObject(final String filename) throws IOException {
        Object object;
        try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(filename))) {
            // read in the object
            object = inStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error - the object could not be read because it is not serializable");
        }
        // return the serializable version of the class
        return (Serializable)object;
    }
}