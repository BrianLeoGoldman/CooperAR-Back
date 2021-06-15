package ar.edu.unq.tip.backendcooperar.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
}
