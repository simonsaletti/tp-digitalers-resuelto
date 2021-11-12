package com.digitalers.spring.boot.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

    public static final String RUTA_CARPETA_IMAGENES =
            "C:\\Users\\Lab JAVA\\Desktop\\Archivos para clase EducacionIT\\Spring\\eclipse-workspace-spring-boot - copia\\"
                    + "zz-carrito-compras-spring-boot\\src\\main\\resources\\static\\img";

    public static void guardarArchivo(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            Path uploadPath = Paths.get(RUTA_CARPETA_IMAGENES);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Error al guardar el archivo: " + fileName, ioe);
            }
        }

    }

    // Hacer un método para eliminar el archivo.

}
