package com.gesschoolapp.serial;

import com.gesschoolapp.Exceptions.ArchiveManagerException;
import com.gesschoolapp.models.actions.Action;
import com.gesschoolapp.models.actions.Actions;

import java.io.*;

public class ActionManager {

    private static final String FILENAME = "storage/actions/actions.ser";


    public static void SerializeActions(Actions actions) throws ArchiveManagerException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(actions);
        } catch (Exception e) {
            throw new ArchiveManagerException("Error while serializing actions : \n" + e.getClass().getName() + " : " + e.getMessage());
        }
    }

    public static Actions DeserializeActions() throws ArchiveManagerException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            return (Actions) ois.readObject();
        }catch (FileNotFoundException e){
            return new Actions();
        }
        catch (Exception e) {
            throw new ArchiveManagerException("Error while deserializing actions : \n" + e.getClass().getName() + " : " + e.getMessage());
        }
    }

    public static void DeleteArchive() throws ArchiveManagerException {
        try {
            Actions actions = DeserializeActions();
            actions.getListActions().clear();
            SerializeActions(actions);
        } catch (ArchiveManagerException e) {
            throw new ArchiveManagerException("Error while deleting actions : \n" + e.getClass().getName() + " : " + e.getMessage());
        }
    }

    /**
     * @param action
     * @throws ArchiveManagerException
     *
     * Every time an action is done you create an action that you pass to this method, the action will
     * be saved in the list of actions and serialized.
     * If the file doesn't exist, or if the file is empty, it will create a new list of actions and
     * add the action to it.
     */
    public static void add(Action action) throws ArchiveManagerException {
        Actions actions = DeserializeActions();
        actions.add(action);
        SerializeActions(actions);
    }
}
