package com.gesschoolapp.serial;

import com.gesschoolapp.Exceptions.ArchiveManagerException;
import com.gesschoolapp.models.actions.Action;

import java.io.*;

public class ActionManager {

    public static byte[] SerializeObjectToByteArray(Object object) throws ArchiveManagerException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new ArchiveManagerException("Error while serializing actions : \n" + e.getClass().getName() + " : " + e.getMessage());
        }
    }

    public static Object DeserializeObjectFromByteArray(byte[] bytes) throws ArchiveManagerException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return ois.readObject();
        } catch (Exception e) {
            throw new ArchiveManagerException("Error while deserializing actions : \n" + e.getClass().getName() + " : " + e.getMessage());
        }
    }

}
