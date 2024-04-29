package dev.ruben.atp.configuration;

import dev.ruben.atp.services.StorageService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración para la gestión de ficheros de almacenamiento
 */
@ConfigurationProperties("storage")
@EnableConfigurationProperties(StorageProperties.class)
@Slf4j
public class StorageProperties {
    private final StorageService storageService;

    @Value("${upload.delete}")
    private String deleteAll;


    public StorageProperties(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostConstruct
    public void init() {
        if (deleteAll.equals("true")) {
            log.info("Borrando ficheros de almacenamiento...");
            storageService.deleteAll();
        }

        storageService.init();
    }
}