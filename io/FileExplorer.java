package com.techupstudio.otc_chingy.mychurch.utils.io;

import com.techupstudio.otc_chingy.mychurch.utils.general.collections.Stack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//TODO : make files to copy or cut a list

public class FileExplorer {

    private File HOME;
    private File CURRENT_FILE;
    private String TRANSFER_STATE;
    private Stack<File> FORWARD_STACK;
    private Stack<File> BACKWARD_STACK;
    private List<File> TEMP_FILES_LIST;

    public FileExplorer() {
        CURRENT_FILE = new File(Paths.get("").toAbsolutePath().toString());
        initializeFields();
    }

    public FileExplorer(File directory) {
        CURRENT_FILE = directory;
        initializeFields();
    }

    public FileExplorer(String directoryPath) {
        if (directoryPath.equals("")) {
            CURRENT_FILE = new File(Paths.get("").toAbsolutePath().toString());
        } else {
            CURRENT_FILE = new File(directoryPath);
        }
        initializeFields();
    }

    public FileExplorer(FileExplorer fileExplorer) {
        CURRENT_FILE = fileExplorer.getCurrentFile();
        TRANSFER_STATE = fileExplorer.getTransferState();
        TEMP_FILES_LIST = fileExplorer.getFilesToTransfer();
        initializeFields();
    }

    private void initializeFields() {
        FORWARD_STACK = new Stack<>();
        BACKWARD_STACK = new Stack<>();
        HOME = CURRENT_FILE;
        TEMP_FILES_LIST = new ArrayList<>();
    }

    public void setHome(File file) {
        HOME = file;
    }

    public void setHome(String filePath) {
        HOME = new File(filePath);
    }

    public void setHome(FileExplorer file) {
        HOME = file.getCurrentFile();
    }

    private void setTransferStateCopy() {
        this.TRANSFER_STATE = "COPY";
    }

    private void setTransferStateCut() {
        this.TRANSFER_STATE = "CUT";
    }

    private String getTransferState() {
        return TRANSFER_STATE;
    }

    public List<File> getFilesToTransfer() {
        return TEMP_FILES_LIST;
    }

    public FileExplorer createNewFolder(String childName) {
        FileManager.createDirectory(CURRENT_FILE, childName);
        return this;
    }

    public FileExplorer createNewFile(String fileName) {
        FileManager.createFile(CURRENT_FILE, fileName);
        return this;
    }

    public FileExplorer renameFile(String oldName, String newName) {
        if (getFile(oldName).exists()) {
            getFile(oldName).renameTo(new File(CURRENT_FILE, newName));
        }
        return this;
    }

    public FileExplorer deleteFile(String childName) {
        if (getFile(childName).exists()) {
            getFile(childName).delete();
        }
        return this;
    }

    public FileExplorer copy(String... fileNames) {
        TEMP_FILES_LIST.clear();
        for (String name : fileNames) {
            TEMP_FILES_LIST.add(getFile(name));
        }
        setTransferStateCopy();
        return this;
    }

    public FileExplorer copyCurrentFile() {
        TEMP_FILES_LIST.clear();
        TEMP_FILES_LIST.add(getCurrentFile());
        setTransferStateCopy();
        return this;
    }

    public FileExplorer cut(String... fileNames) {
        TEMP_FILES_LIST.clear();
        for (String name : fileNames) {
            TEMP_FILES_LIST.add(getFile(name));
        }
        setTransferStateCut();
        return this;
    }

    public FileExplorer cutCurrentFile() {
        TEMP_FILES_LIST.clear();
        TEMP_FILES_LIST.add(getCurrentFile());
        setTransferStateCut();
        return this;
    }

    public FileExplorer paste() {
        if (getCurrentFile().isDirectory()) {
            if (getTransferState().equals("COPY")) {
                for (File file : TEMP_FILES_LIST) {
                    FileManager.copy(file, getCurrentFile());
                }
            } else if (getTransferState().equals("CUT")) {
                for (File file : TEMP_FILES_LIST) {
                    FileManager.cut(file, getCurrentFile());
                }
            }
        }
        return this;
    }

    public FileExplorer pasteWithNewName(String newName) {
        if (getCurrentFile().isDirectory()) {
            if (getTransferState().equals("COPY")) {
                for (File file : TEMP_FILES_LIST) {
                    FileManager.copy(file, getCurrentFile(), newName);
                }
            } else if (getTransferState().equals("CUT")) {
                for (File file : TEMP_FILES_LIST) {
                    FileManager.cut(file, getCurrentFile(), newName);
                }
            }
        }
        return this;
    }

    public FileExplorer pasteIn(String folderName) {
        pasteIn(getFile(folderName));
        return this;
    }

    public FileExplorer pasteWithNewNameIn(String folderName, String newName) {
        pasteWithNewNameIn(getFile(folderName), newName);
        return this;
    }

    public FileExplorer pasteIn(File directory) {
        if (directory.isDirectory()) {
            if (getTransferState().equals("COPY")) {
                for (File file : TEMP_FILES_LIST) {
                    FileManager.copy(file, directory);
                }
            } else if (getTransferState().equals("CUT")) {
                for (File file : TEMP_FILES_LIST) {
                    FileManager.cut(file, directory);
                }
            }
        }
        return this;
    }

    public FileExplorer pasteWithNewNameIn(File directory, String newName) {
        if (directory.isDirectory()) {
            if (getTransferState().equals("COPY")) {
                for (File file : TEMP_FILES_LIST) {
                    FileManager.copy(file, directory, newName);
                }
            } else if (getTransferState().equals("CUT")) {
                for (File file : TEMP_FILES_LIST) {
                    FileManager.cut(file, directory, newName);
                }
            }
        }
        return this;
    }

    public boolean isFile() {
        return CURRENT_FILE.isFile();
    }

    public boolean isFolder() {
        return CURRENT_FILE.isDirectory();
    }

    public URI getCurrentFileURI() {
        return CURRENT_FILE.toURI();
    }

    public ReadWritableFile openCurrentFileAsReadWritableFile() throws IOException {
        if (isFile()) {
            return new ReadWritableFile(getCurrentFile());
        }
        return null;
    }

    public ReadWritableFile openReadWritableFile(String fileName) throws IOException {
        if (getFile(fileName).isFile()) {
            return new ReadWritableFile(getFile(fileName));
        }
        return null;
    }

    public String getCurrentPath() {
        return CURRENT_FILE.getAbsolutePath();
    }

    public String makeFilePath(String newPathName) {
        return getCurrentPath() + "/" + newPathName;
    }

    public String[] getFileNames() {
        String[] files = CURRENT_FILE.list();
        return (files == null) ? new String[]{} : files;
    }

    public File[] getFileItems() {
        File[] files = CURRENT_FILE.listFiles();
        return (files == null) ? new File[]{} : files;
    }

    public FileExplorer openFolder(String folderName) throws FileNotDirectoryException, FileNotFoundException {
        if (getFile(folderName).exists()) {
            if (getFile(folderName).isDirectory()) {
                return new FileExplorer(getFile(folderName));
            }
            throw new FileNotDirectoryException();

        }
        throw new FileNotFoundException();
    }

    public FileExplorer openNonNullFolder(String folderName) throws FileNotDirectoryException {
        if (getFile(folderName).exists()) {
            if (getFile(folderName).isDirectory()) {
                return new FileExplorer(getFile(folderName));
            }
            throw new FileNotDirectoryException();
        } else {
            if (getFile(folderName).mkdirs()) {
                return openNonNullFolder(folderName);
            }
        }
        return null;
    }

    public FileExplorer exploreFile(String fileName) throws FileNotFoundException {
        if (getFile(fileName).exists()) {
            BACKWARD_STACK.push(getCurrentFile());
            CURRENT_FILE = getFile(fileName);
        } else {
            throw new FileNotFoundException();
        }
        return this;
    }

    public FileExplorer goBack() throws FileNotFoundException {
        if (BACKWARD_STACK.isEmpty()) {
            return null;
        }
        if (BACKWARD_STACK.peekTop().exists()) {
            FORWARD_STACK.push(getCurrentFile());
            CURRENT_FILE = BACKWARD_STACK.pop();
        } else {
            throw new FileNotFoundException();
        }
        return this;
    }

    public FileExplorer goForward() throws FileNotFoundException {
        if (FORWARD_STACK.isEmpty()) {
            return null;
        }
        if (FORWARD_STACK.peekTop().exists()) {
            BACKWARD_STACK.push(getCurrentFile());
            CURRENT_FILE = FORWARD_STACK.pop();
        } else {
            throw new FileNotFoundException();
        }
        return this;
    }

    public FileExplorer goHome() throws FileNotFoundException {
        if (HOME.exists()) {
            if (CURRENT_FILE != HOME) {
                BACKWARD_STACK.push(getCurrentFile());
                BACKWARD_STACK.push(getCurrentFile());
                CURRENT_FILE = HOME;
            }
        } else {
            throw new FileNotFoundException();
        }
        return this;
    }

    public File[] searchFolderWithPattern(String pattern) {
        List<File> matchesList = new ArrayList<>();
        for (File file : getFileItems()) {
            String name = file.getName();
            if (name.toLowerCase().contains(pattern.toLowerCase())) {
                matchesList.add(file);
            }
        }
        File[] matchesArray = new File[matchesList.size()];
        matchesList.toArray(matchesArray);
        return matchesArray;
    }

    public FileExplorer getBackwardFile() {
        return new FileExplorer(BACKWARD_STACK.peekTop());
    }

    public FileExplorer getForwardFile() {
        return new FileExplorer(FORWARD_STACK.peekTop());
    }

    public File getCurrentFile() {
        return CURRENT_FILE;
    }

    public File getFile(String fileName) {
        return new File(CURRENT_FILE, fileName);
    }

    public FileExplorer ls() {
        if (CURRENT_FILE.isDirectory()) {
            FileManager.listDir(CURRENT_FILE);
        }
        return this;
    }

    public FileExplorer clone() {
        return new FileExplorer(this);
    }

    public void forEachFileOrFolder(FileExplorer.FileProcess fileProcess) {
        Objects.requireNonNull(fileProcess);

        for (File file : getFileItems()) {
            fileProcess.process(file);
        }
    }

    public void forEachFileInFolderAndSubFolders(final FileProcess fileProcess) {
        Objects.requireNonNull(fileProcess);

        forEachSubFolder(new FileProcess() {
            @Override
            public void process(File file) {
                new FileExplorer(file).forEachFile(new FileProcess() {
                    @Override
                    public void process(File file) {
                        fileProcess.process(file);
                    }
                });
            }
        });

    }

    public void forEachSubFolder(final FileProcess fileProcess) {
        Objects.requireNonNull(fileProcess);

        forEachFolder(new FileProcess() {
            private void action(File file) {
                if (file != null) {
                    fileProcess.process(file);
                    new FileExplorer(file).forEachFolder(new FileProcess() {
                        @Override
                        public void process(File file) {
                            action(file);
                        }
                    });
                }
            }

            @Override
            public void process(File file) {
                action(file);
            }
        });
    }

    public void forEachFile(final FileProcess fileProcess) {
        Objects.requireNonNull(fileProcess);

        forEachFileOrFolder(new FileProcess() {
            @Override
            public void process(File file) {
                if (file.isFile()) {
                    fileProcess.process(file);
                }
            }
        });
    }

    public void forEachFolder(final FileProcess fileProcess) {
        Objects.requireNonNull(fileProcess);

        forEachFileOrFolder(new FileProcess() {
            @Override
            public void process(File file) {
                if (file.isDirectory()) {
                    fileProcess.process(file);
                }
            }
        });
    }

    public boolean contains(String fileName) {
        System.out.print("searching file : " + fileName + " -- {");
        int index = Arrays.binarySearch(getFileNames(), fileName);
        System.out.print("\n\t\tresult : " + index + " ,");
        return index >= 0;
    }

    public void put(File file) {
        FileManager.copy(file, CURRENT_FILE);
    }

    public void put(File file, String newName) {
        FileManager.copy(file, CURRENT_FILE, newName);
    }

    public void forEachFileOrFolderLike(final String pattern, final FileProcess fileProcess) {
        Objects.requireNonNull(fileProcess);

        forEachFileOrFolder(new FileProcess() {
            @Override
            public void process(File file) {
                if (file.getName().toLowerCase().contains(pattern.toLowerCase())) {
                    fileProcess.process(file);
                }
            }
        });
    }

    public void forEachSubFileOrFolderLike(final String pattern, final FileProcess fileProcess) {
        Objects.requireNonNull(fileProcess);

        forEachFileOrFolder(new FileProcess() {

            private void action(File file) {
                if (file.getName().toLowerCase().contains(pattern.toLowerCase())) {
                    fileProcess.process(file);
                }
                if (file.isDirectory()) {
                    new FileExplorer(file).forEachFileOrFolder(new FileProcess() {
                        @Override
                        public void process(File file) {
                            action(file);
                        }
                    });
                }
            }

            @Override
            public void process(File file) {
                action(file);
            }
        });
    }

    public interface FileProcess {
        void process(File file);
    }

    public class FileNotDirectoryException extends Exception {
        FileNotDirectoryException() {
        }
    }
}