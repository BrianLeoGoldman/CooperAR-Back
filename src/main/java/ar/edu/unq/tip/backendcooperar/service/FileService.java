package ar.edu.unq.tip.backendcooperar.service;

import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class FileService {

    public void postFile(MultipartFile file, String id, String type) throws IOException {
        // Maximum size of the file: 1048576 bytes or 1048.576 kilobytes
        String directory = "src/main/resources/" + type + "/" + id + "/";
        Files.createDirectories(Paths.get(directory));
        File newFile = new File(directory + file.getOriginalFilename());
        try (OutputStream os = new FileOutputStream(newFile)) {
            os.write(file.getBytes());
        }
    }

    public void deleteDirectoryAndFiles(String directory) {
        File index = new File(directory);
        String[]entries = index.list();
        if(entries != null && entries.length > 0){
            for(String s: entries){
                File currentFile = new File(index.getPath(),s);
                currentFile.delete();
            }
            index.delete();
        }
    }

    public File getFile(String filePath) throws IOException {
        PathResource path = new PathResource(filePath);
        return path.getFile();
    }

    public File[] getFilesFromDirectory(String directory){
        File folder = new File(directory);
        return folder.listFiles();
    }

    public String encodeImage(File file) throws IOException {
        return Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath()));
    }
}
