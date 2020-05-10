package training.start.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import training.start.exception.FileStorageException;
import training.start.model.DatabaseFile;
import training.start.repository.DatabaseFileRepository;

@Service
public class DatabaseFileService {

    @Autowired
    private DatabaseFileRepository dbFileRepository;
    
    private final Path root = Paths.get("uploads");

    public DatabaseFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes());
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    public Resource load(long id) {
        try {
          Path file = root.resolve("Cv_Youssef.docx");
          System.out.println("test :"+id);
          Resource resource = new UrlResource(file.toUri());

          if (resource.exists() || resource.isReadable()) {
            return resource;
          } else {
            throw new RuntimeException("Could not read the file!");
          }
        } catch (MalformedURLException e) {
          throw new RuntimeException("Error: " + e.getMessage());
        }
      }

      
      public Stream<Path> loadAll() {
        try {
          return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
          throw new RuntimeException("Could not load the files!");
        }
      }
	
}