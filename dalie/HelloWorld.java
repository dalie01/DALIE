package dalie;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HelloWorld {

    private static final String s1;

    static {
        s1 = "dalie.HelloWorld";
    }

    public static void main(String[] args) {
        // Define source and destination folders
        Path sourcePath = Paths.get("C:", "edeveloper", "zdp");
        Path destinationPath = Paths.get("C:", "Users", "zoudalie", "OneDrive - Carl Zeiss AG", "edeveloper", "zdp");
        // Define the prefix to exclude
        Set<String> toSkipFolders = new HashSet<>(Arrays.asList(".git,.idea,out,target".split(",")));
        Set<String> toSkipFiles = new HashSet<>(Arrays.asList("iml,.gitAttributes".split(",")));
        try {
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    String name = dir.getFileName().toString();
                    if (toSkipFolders.contains(name)) {
                        return FileVisitResult.SKIP_SUBTREE;
                    } else {
                        return FileVisitResult.CONTINUE;
                    }
                }

                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {

                    Path destinationFile = destinationPath.resolve(sourcePath.relativize(path));
                    String fileType = destinationFile.toString();

                    if (!fileType.endsWith("iml") && !fileType.endsWith(".gitattributes")) {
//                        System.out.println(destinationFile.getParent().toAbsolutePath().toString());
                        if (!Files.exists(destinationFile.getParent())) {
                            Files.createDirectories(destinationFile.getParent());

                        }
                        BasicFileAttributes sourceAttrs = Files.readAttributes(path, BasicFileAttributes.class);
                        BasicFileAttributes destAttrs = null;
                        if (Files.exists(destinationFile)) {
                            destAttrs = Files.readAttributes(destinationFile, BasicFileAttributes.class);
                        }
//                        try {
                            if (destAttrs == null || sourceAttrs.lastModifiedTime().compareTo(destAttrs.lastModifiedTime()) > 0) {
//                                Files.copy(path, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                                System.out.println("Copying file " + path.toAbsolutePath().toString() + " to destination " + destinationPath.toAbsolutePath().toString());
                                if (destAttrs != null) {

                                System.out.println("from " + sourceAttrs.lastModifiedTime() + " to " + destAttrs.lastModifiedTime() + " compare is " + (sourceAttrs.lastModifiedTime().compareTo(destAttrs.lastModifiedTime()) > 0));
                                }
                            } else {
//                                System.out.println("Skipping file " + destinationFile.toAbsolutePath().toString() + " as destination file is already up to date.");
                            }
//                        } catch (FileAlreadyExistsException e) {
//                            System.out.println("Skipping file " + sourcePath.getFileName() + " as destination file already exists.");
//                        }
                    }



                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}