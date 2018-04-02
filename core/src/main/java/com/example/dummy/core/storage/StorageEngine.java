package com.example.dummy.core.storage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import okhttp3.internal.platform.Platform;

public class StorageEngine {

    public static final String SEPARATOR = "/";

    private static String filesDirName;

    public static void init(String filesDirName) {
        StorageEngine.filesDirName = filesDirName;
    }

    public static void write(String destination, Object data) {
        try {
            File filesDir = new File(filesDirName + StorageEngine.SEPARATOR);
            if (!filesDir.exists()) {
                filesDir.mkdirs();
            }
            File file = new File(filesDirName + StorageEngine.SEPARATOR + "_se_" + destination);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(data);
            os.flush();
        } catch (IOException e) {
            Platform.get().log(Platform.WARN, "Cant write to file " + destination, null);
        }
    }

    public static boolean writeFromStream(String destination, InputStream data) throws IOException {

        File filesDir = new File(filesDirName);

        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }
        File file = new File(filesDir + StorageEngine.SEPARATOR + "_se_" + destination);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        FileOutputStream os = new FileOutputStream(file);
        while (true) {
            byte[] fileReader = new byte[4096];
            int read = data.read(fileReader);
            if (read == -1) {
                break;
            }
            os.write(fileReader, 0, read);
        }

        os.flush();

        return true;
    }

    public static BufferedInputStream readIntoStream(String destination) throws IOException {

        File file = new File(filesDirName + StorageEngine.SEPARATOR + "_se_" + destination);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        FileInputStream in = new FileInputStream(file);
        return new BufferedInputStream(in);
    }

    public static String checkExists(String destination) throws IOException {

        File file = new File(filesDirName + StorageEngine.SEPARATOR + "_se_" + destination);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        return file.getAbsolutePath();
    }

    public static boolean checkExistsSafe(String destination) {

        try {
            checkExists(destination);
            return true;
        } catch (IOException e) {
            Platform.get().log(Platform.WARN, "Cant check if exists file " + destination, null);
//            e.printStackTrace();
        }
        return false;
    }

    public static Object read(String destination) {

        try {
            File file = new File(filesDirName + StorageEngine.SEPARATOR + "_se_" + destination);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        } catch (ClassNotFoundException | IOException e) {
            Platform.get().log(Platform.WARN, "Cant read from file " + destination, null);
            e.printStackTrace();
        }

        return null;
    }

    public static void drop(String destination) {

        try {
            File file = new File(filesDirName + StorageEngine.SEPARATOR + "_se_" + destination);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            file.delete();
        } catch (IOException e) {
            Platform.get().log(Platform.WARN, "Cant drop file " + destination, null);
            e.printStackTrace();
        }
    }

    public static void dropData() {

        try {
            File file = new File(filesDirName + StorageEngine.SEPARATOR);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            dropFolderOrFile(file);
        } catch (IOException e) {
            Platform.get().log(Platform.WARN, "Cant drop data", null);
            e.printStackTrace();
        }
    }

    private static void dropFolderOrFile(File fileOrFolder) {
        if (fileOrFolder.isDirectory()) {
            for (File subFile : fileOrFolder.listFiles()) {
                dropFolderOrFile(subFile);
            }
        } else {
            fileOrFolder.delete();
        }
    }

    public static String readStringNonNullable(String destination) {
        Object read = read(destination);
        return read != null && read instanceof String ? (String) read : "";
    }

    public static String readStringNullable(String destination) {
        Object read = read(destination);
        return read != null && read instanceof String ? (String) read : null;
    }
}
