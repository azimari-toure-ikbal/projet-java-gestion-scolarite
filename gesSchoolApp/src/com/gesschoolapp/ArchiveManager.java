package com.gesschoolapp;

import com.gesschoolapp.Exceptions.ArchiveManagerException;
import com.gesschoolapp.models.classroom.Classes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ArchiveManager {
    public static final long serialVersionUID = 11555;
    private static final String FILE_TEMPLATE = "archives/archive";


    public static void SerializeArchive(Classes classes) throws ArchiveManagerException{
        String fileName = FILE_TEMPLATE + classes.getYear() + ".ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(classes);
        } catch (Exception e) {
            throw new ArchiveManagerException("Error while serializing classes : \n" + e.getClass().getName() + " : " + e.getMessage());
        }

    }

    public static Classes DeserializeArchive(String year) throws ArchiveManagerException {
        String filename = FILE_TEMPLATE + year + ".ser";
        //deserialize the contacts from C:\workspace-java\files\contacts.ser and return the object
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Classes) ois.readObject();
        } catch (Exception e) {
            throw new ArchiveManagerException("Error while deserializing classes\n" + e.getClass().getName() + " : " + e.getMessage());
        }
    }

    public static void DeleteArchive(String year) throws ArchiveManagerException {
        String filename = FILE_TEMPLATE + year + ".ser";
        try {
            Classes classes = DeserializeArchive(year);
            classes.getClasses().clear();
            SerializeArchive(classes);
        } catch (ArchiveManagerException e) {
            throw new ArchiveManagerException("Error while deleting archive : " + e.getClass().getName() + " : " + e.getMessage());
        }
    }


}
