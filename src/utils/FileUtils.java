package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to read, write and create a file.
 */
public class FileUtils {

  /**
   * Function to create a file.
   *
   * @param path path where the file is to be created
   * @return path where the file is created
   */
  public static Path createFile(String path) {

    Path createdFilePath;
    try {
      createdFilePath = Files.createFile(Path.of(path));
      createdFilePath.toFile().setWritable(true);
    } catch (Exception e) {
      createdFilePath = Paths.get(path);
    }

    return createdFilePath;
  }

  /**
   * Function that write to a file.
   *
   * @param file    file where the content is written
   * @param content content that needs to be written
   */
  public static void writeToFile(Path file, String content) {
    try {
      Files.writeString(file, content);
    } catch (Exception e) {
      //e.printStackTrace();
    }
  }

  /**
   * Function appends content to the end of the file.
   *
   * @param file    file where the content is appended
   * @param content content that needs to be appended
   */
  public static void appendContentToFile(Path file, String content) {
    Path createdFile;
    try {
      createdFile = createFile(file.toString());
      Files.writeString(createdFile, content, StandardOpenOption.APPEND);
    } catch (Exception e) {
      try {
        Files.writeString(file, content, StandardOpenOption.APPEND);
      } catch (IOException ex) {
        //
      }
    }
  }

  /**
   * Function that reads a file.
   *
   * @param file file from which the content is to be read
   * @return list of string in the file
   */
  public static List<String> readFromFile(Path file) {
    try {
      return Files.readAllLines(file);
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

}
