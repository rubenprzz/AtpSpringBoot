package dev.ruben.atp.services;

import dev.ruben.atp.controllers.FileUploadController;
import dev.ruben.atp.exceptions.StorageBadRequest;
import dev.ruben.atp.exceptions.StorageFileNotFoundException;
import dev.ruben.atp.exceptions.StorageInternal;
import dev.ruben.atp.services.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;



@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;


    public FileSystemStorageService(@Value("${upload.root-location}") String path) {
        this.rootLocation = Paths.get(path);
    }


    @Override
    public String store(MultipartFile file) {
        List<String> extensionAcepted = List.of("png", "jpg", "jpeg", "gif");
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = StringUtils.getFilenameExtension(filename);
        if (!extensionAcepted.contains(extension)) {
            throw new StorageBadRequest("Extensión no permitida " + extension);
        }
        String justFilename = filename.replace("." + extension, "");
        String storedFilename = System.currentTimeMillis() + "_" + justFilename.replaceAll("\\s+", "") + "." + extension;

        try {
            if (file.isEmpty()) {
                throw new StorageBadRequest("Fichero vacío " + filename);
            }
            if (filename.contains("..")) {
                throw new StorageBadRequest(
                        "No se puede almacenar un fichero con una ruta relativa fuera del directorio actual "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                log.info("Almacenando fichero " + filename + " como " + storedFilename);
                Files.copy(inputStream, this.rootLocation.resolve(storedFilename),
                        StandardCopyOption.REPLACE_EXISTING);
                return storedFilename;
            }

        } catch (IOException e) {
            throw new StorageInternal("Fallo al almacenar fichero " + filename + " " + e);
        }

    }


    @Override
    public Stream<Path> loadAll() {
        log.info("Cargando todos los ficheros almacenados");
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageInternal("Fallo al leer ficheros almacenados " + e);
        }

    }

    @Override
    public Path load(String filename) {
        log.info("Cargando fichero " + filename);
        return rootLocation.resolve(filename);
    }



    @Override
    public Resource loadAsResource(String filename) {
        log.info("Cargando fichero " + filename);
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("No se puede leer fichero: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("No se puede leer fichero: " + filename + " " + e);
        }
    }



    @Override
    public void deleteAll() {
        log.info("Eliminando todos los ficheros almacenados");
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }



    @Override
    public void init() {
        log.info("Inicializando almacenamiento");
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageInternal("No se puede inicializar el almacenamiento " + e);
        }
    }


    @Override
    public void delete(String filename) {
        String justFilename = StringUtils.getFilename(filename);
        try {
            log.info("Eliminando fichero " + filename);
            Path file = load(justFilename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageInternal("No se puede eliminar el fichero " + filename + " " + e);
        }

    }


    @Override
    public String getUrl(String filename) {
        log.info("Obteniendo URL del fichero " + filename);
        return MvcUriComponentsBuilder

                .fromMethodName(FileUploadController.class, "serveFile", filename, null)
                .build().toUriString();
    }

}