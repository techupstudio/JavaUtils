package com.techupstudio.otc_chingy.mychurch.core.utils.io;

import com.techupstudio.otc_chingy.mychurch.core.utils.general.Funcs;
import com.techupstudio.otc_chingy.mychurch.core.utils.general.testing.Preconditions;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.techupstudio.otc_chingy.mychurch.core.utils.general.Funcs.println;

public class FileManager {

    public static final int DEFAULT_BUFFER_SIZE = 1024;

    private FileManager() {
    }

    public static boolean makeFile(File parent, String fileName) {
        return makeFile(new File(parent, fileName));
    }

    public static boolean makeDirs(File parent, String childName) {
        File temp = new File(parent, childName);
        return temp.exists() || temp.mkdirs();
    }

    public static boolean makeDirs(File file) {
        if (!file.exists()) {
            if (isFile(file)) {
                file = file.getParentFile();
            }
            return file != null && file.mkdirs();
        }
        return true;
    }

    public static boolean makeFile(File file) {
        try {
            return file.exists() || ((file.getParentFile() == null
                            || file.getParentFile().exists()
                            || file.getParentFile().mkdirs()) && file.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean makeFileOrFolder(File file) {
        if (file == null)
            return false;

        if (!file.exists()) {
            if (isFile(file)) {
                return makeFile(file);
            } else {
                return makeDirs(file);
            }
        } else {
            return true;
        }
    }

    public static boolean isFile(File file) {
        return file != null && ((file.exists() && file.isFile()) || !getExtension(file.getName()).isEmpty());
    }

    public static boolean isDirectory(File file) {
        return file != null && ((file.exists() && file.isDirectory()) || getExtension(file.getName()).isEmpty());
    }

    public static File getFile(File parent, String fileName) {
        File temp = new File(parent, fileName);
        if (temp.exists()) {
            return temp;
        }
        return null;
    }

    public static File requireFile(File parent, String childName) {
        return new File(parent, childName);
    }

    public static File requireOrCreateFile(File parent, String childName) {
        File file = requireFile(parent, childName);
        if (makeFileOrFolder(file)) {
            return file;
        }
        throw new Error("could not get or create the file!.");
    }

    public static void listDir(File file) {
        String[] paths;
        try {
            paths = file.list();
            if (paths != null)
                for (String path : paths)
                    System.out.println(path);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(File file, String line) {

        FileWriter WRITER;

        if (file.canWrite()) {
            try {
                WRITER = new FileWriter(file);
                WRITER.write(line);
                WRITER.flush();
                WRITER.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeAppend(File file, String lines) {

        FileWriter WRITER;

        if (file.canWrite()) {
            try {
                WRITER = new FileWriter(file);
                WRITER.write(read(file) + "\n" + lines);
                WRITER.flush();
                WRITER.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String read(File file) {
        String retStr = "";

        FileReader READER = null;

        if (file.canRead()) {
            try {
                READER = new FileReader(file);
                while (true) {
                    int c = READER.read();
                    if (c != -1) {
                        retStr += (char) c;
                    } else {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            assert READER != null;
            READER.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retStr;
    }

    public static Map<Integer, String> readEnumerate(File file) {

        Map<Integer, String> lines = new HashMap<>();

        String retStr = "";

        FileReader READER = null;

        if (file.canRead()) {
            try {
                READER = new FileReader(file);
                int i = 1;
                while (true) {
                    int c = READER.read();
                    if (c != -1) {
                        if (c == 10) {
                            if (!retStr.trim().isEmpty()) {
                                lines.put(i, retStr);
                                retStr = "";
                                i++;
                            }
                        } else {
                            retStr += (char) c;
                        }
                    } else {
                        if (!retStr.trim().isEmpty()) {
                            lines.put(i, retStr);
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                assert READER != null;
                READER.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    public static void writeUTF(File file, String lines) {
        if (file.canWrite()) {
            DataOutputStream dataOut = null;
            try {
                dataOut = new DataOutputStream(new FileOutputStream(file.getAbsolutePath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                assert dataOut != null;
                dataOut.writeUTF(lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readUTF(File file) {
        StringBuilder retStr = new StringBuilder();

        if (file.canRead()) {
            DataInputStream dataIn = null;
            try {
                dataIn = new DataInputStream(new FileInputStream(file.getAbsolutePath()));
                while (dataIn.available() > 0) {
                    String c = dataIn.readUTF();
                    retStr.append(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retStr.toString();
    }


    public static boolean copy(File sourceFile, File destinationFile) {
        Preconditions.checkArgument(sourceFile != null, "Source file can't be null!");
        Preconditions.checkArgument(destinationFile != null, "Destination file can't be null!");
        if (sourceFile.exists()) {
            if (sourceFile.isFile()) {
                if (destinationFile.isFile()) {
                    Preconditions.checkArgument(destinationFile.getParentFile() != null, "Destination file must have a parent!");
                    return copyFile(sourceFile, destinationFile.getParentFile(), destinationFile.getName());
                } else {
                    return copyFile(sourceFile, destinationFile, null);
                }
            } else {
                Preconditions.checkArgument(destinationFile.isDirectory(), "Destination file must be a directory!");
                return copyDirectory(sourceFile, destinationFile, destinationFile.getName());
            }
        }
        return false;
    }

    public static boolean copy(File sourceFile, File destinationDirectory, String name) {
        Preconditions.checkArgument(destinationDirectory != null, "Destination directory can't be null!");
        Preconditions.checkArgument(destinationDirectory.isDirectory(), "Destination file must be a directory!");
        return copy(sourceFile, new File(destinationDirectory, name));
    }

    public static boolean cut(File sourceFile, File destinationFile) {
        return copy(sourceFile, destinationFile) && sourceFile.delete();
    }

    public static boolean cut(File sourceFile, File destinationDirectory, String name) {
        Preconditions.checkArgument(destinationDirectory != null, "Destination directory can't be null!");
        Preconditions.checkArgument(destinationDirectory.isDirectory(), "Destination file must be a directory!");
        return cut(sourceFile, new File(destinationDirectory, name));
    }

    public static boolean save(byte[] bytes, File destinationFile, int bufferSize) {
        try {
            return save(bytes, new FileOutputStream(destinationFile), bufferSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean save(byte[] bytes, File file) {
        return save(bytes, file, DEFAULT_BUFFER_SIZE);
    }

    public static boolean save(byte[] bytes, OutputStream outputStream, int bufferSize) {
        Preconditions.checkArgument(bytes != null, "Bytes can't be a null!");
        Preconditions.checkArgument(outputStream != null, "Output stream can't be a null!");
        return save(new ByteArrayInputStream(bytes), outputStream, bufferSize);
    }

    public static boolean save(byte[] bytes, OutputStream outputStream) {
        return save(bytes, outputStream, DEFAULT_BUFFER_SIZE);
    }

    public static boolean save(File sourceFile, File destinationFile, int bufferSize) {
        Preconditions.checkArgument(sourceFile != null, "Source file can't be null");
        Preconditions.checkArgument(sourceFile.isFile(), "Source file can't be a directory");
        Preconditions.checkArgument(destinationFile != null, "Destination file can't be null");
        Preconditions.checkArgument(destinationFile.isFile(), "Destination file can't be a directory");
        Preconditions.checkArgument(sourceFile.exists(), "Source file must exist!");
        try {
            return save(new FileInputStream(sourceFile), new FileOutputStream(destinationFile), bufferSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean save(File sourceFile, File destinationFile) {
        return save(sourceFile, destinationFile, DEFAULT_BUFFER_SIZE);
    }

    public static boolean save(InputStream inputStream, OutputStream outputStream, int bufferSize) {
        Preconditions.checkArgument(inputStream != null, "Input stream can't be null!");
        Preconditions.checkArgument(outputStream != null, "Output stream can't be a null!");
        Preconditions.checkArgument(bufferSize > 0, "buffer size must be a positive integer!.");
        try {
            int len;
            byte[] buffer = new byte[bufferSize];
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean save(InputStream inputStream, OutputStream outputStream) {
        return save(inputStream, outputStream, DEFAULT_BUFFER_SIZE);
    }

    private static boolean copyFile(File sourceFile, File destinationDirectory, String name) {
        Preconditions.checkArgument(sourceFile != null, "Source file can't be null");
        Preconditions.checkArgument(sourceFile.isFile(), "Source file can't be a directory");
        Preconditions.checkArgument(destinationDirectory != null, "Destination directory can't be null");
        Preconditions.checkArgument(destinationDirectory.isDirectory(), "Destination directory can't be a file");
        try {
            if (destinationDirectory.exists() || destinationDirectory.mkdirs()) {
                File copiedFile = makeCopiedFile(sourceFile, destinationDirectory, name);
                if (copiedFile.exists() || copiedFile.createNewFile()) {
                    FileInputStream inputStream = new FileInputStream(sourceFile);
                    FileOutputStream outputStream = new FileOutputStream(copiedFile);
                    return save(inputStream, outputStream);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean copyDirectory(File sourceDirectory, File destinationDirectory, String name) {
        Preconditions.checkArgument(sourceDirectory != null, "Source directory can't be null");
        Preconditions.checkArgument(sourceDirectory.isDirectory(), "Source directory can't be a file");
        Preconditions.checkArgument(destinationDirectory != null, "Destination directory can't be null");
        Preconditions.checkArgument(destinationDirectory.isDirectory(), "Destination directory can't be a file");
        try {
            if (sourceDirectory.isDirectory()) {

                if (destinationDirectory.exists() || destinationDirectory.mkdir()) {

                    File copiedDirectory = makeCopiedFile(sourceDirectory, destinationDirectory, name);

                    if (copiedDirectory.exists() || copiedDirectory.mkdirs()) {
                        File[] fileList = sourceDirectory.listFiles();
                        if (fileList != null) {
                            for (File file : fileList) {
                                if (file.isFile()) {
                                    copyFile(file, copiedDirectory, null);
                                } else {
                                    copyDirectory(file, copiedDirectory, null);
                                }
                            }
                        }
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String renameToCopiedFile(String name) {
        if (!name.contains(".")) {
            return name + "_copy";
        } else if (name.lastIndexOf(".") == 0) {
            return name + "_copy";
        }
        String nameOnly = name.substring(0, name.lastIndexOf("."));
        return nameOnly + "_copy" + name.substring(name.lastIndexOf("."));
    }

    public static boolean renameFile(File file, String name) {
        if (file.exists()) {
            String pathName = file.getAbsolutePath()
                    .substring(0, file.getAbsolutePath().lastIndexOf(file.getName())) + name;
            return file.renameTo(new File(pathName));
        }
        return false;
    }

    public static String getExtension(String name) {
        if (name == null) {
            return null;
        } else if (!name.contains(".")) {
            return "";
        } else if (name.lastIndexOf(".") == 0) {
            return "";
        }
        return name.substring(name.lastIndexOf("."));
    }

    private static File makeCopiedFile(File sourceFile, File destinationFile, String name) {

        File copiedFile = destinationFile;

        if (copiedFile.isDirectory()) {
            if (name == null || name.trim().isEmpty()) {
                if (requireFile(destinationFile, sourceFile.getName()).exists()) {
                    copiedFile = requireFile(destinationFile, renameToCopiedFile(sourceFile.getName()));
                    while (copiedFile.exists()) {
                        copiedFile = requireFile(destinationFile, renameToCopiedFile(copiedFile.getName()));
                    }
                } else {
                    copiedFile = requireFile(destinationFile, sourceFile.getName());
                }
            } else {

                if (!getExtension(name).equals(getExtension(sourceFile.getName()))) {
                    name += getExtension(sourceFile.getName());
                }

                if (requireFile(destinationFile, name).exists()) {
                    copiedFile = requireFile(destinationFile, renameToCopiedFile(sourceFile.getName()));
                } else {
                    copiedFile = requireFile(destinationFile, name);
                }
            }
        }
        return copiedFile;
    }

    public static String getEnviron(String name) {
        return System.getenv(name);
    }

    public static void listEnviron() {
        Funcs.forEach(System.getenv(), (key, value) -> println(key + ": " + value));
    }

    public static File getPathWithoutFilename(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                // no file to be split off. Return everything
                return file;
            } else {
                String filename = file.getName();
                String filepath = file.getAbsolutePath();

                // Construct path without file name.
                String pathwithoutname = filepath.substring(0,
                        filepath.length() - filename.length());
                if (pathwithoutname.endsWith("/")) {
                    pathwithoutname = pathwithoutname.substring(0, pathwithoutname.length() - 1);
                }
                return new File(pathwithoutname);
            }
        }
        return null;
    }

    private static long sizeOfFile(File file, int pow) {
        return (long) (file.length() / (Math.pow(DEFAULT_BUFFER_SIZE, pow)));
    }

    public static long sizeInBytes(File file) {
        return sizeOfFile(file, 0);
    }

    public static long sizeInKiloBytes(File file) {
        return sizeOfFile(file, 1);
    }

    public static long sizeInMegaBytes(File file) {
        return sizeOfFile(file, 2);
    }

    public static long sizeInGigaBytes(File file) {
        return sizeOfFile(file, 3);
    }

    public static long sizeInTeraBytes(File file) {
        return sizeOfFile(file, 4);
    }

}