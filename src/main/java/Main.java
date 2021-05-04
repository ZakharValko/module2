import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        String directoryToSearch = "";
        String fileToSearch = "test.txt";
        long minSize = 5;
        long maxSize = 10;
        String regEx = "t.*";
        System.out.println(findFile(directoryToSearch, fileToSearch, minSize, maxSize, regEx));
    }

    public static List<Path> findFile(String directory, String name, long minSize, long maxSize, String regEx) throws IOException{
        try(Stream<Path> files = Files.walk(Paths.get(directory))){
            return files
                    .filter(f -> f.getFileName().toString().equals(name))
                    .filter(f -> {
                        try {
                            return Files.size(f) < maxSize && Files.size(f) > minSize;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return false;
                    })
                    .filter(f -> f.toFile().getName().matches(regEx))
                    .map(Path::toAbsolutePath)
                    .collect(Collectors.toList());
        }
    }
}
