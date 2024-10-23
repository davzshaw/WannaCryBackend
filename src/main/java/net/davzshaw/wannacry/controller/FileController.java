package net.davzshaw.wannacry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.davzshaw.wannacry.services.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    private FileService fileService;

    @DeleteMapping("/clearfiles")
    public ResponseEntity<String> clear() {
        try {
            fileService.clearFile();

            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Files cleared successfully");

        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Error trying to clear files: " + e.getMessage());
        }
    }
    
    @PostMapping("/uploadtemperature/{temp}")
    public ResponseEntity<String> saveTemperature(@PathVariable String temp) {
        try {
            
            fileService.saveTemperature(temp);

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Temperature saved successfully");

        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error trying to save temperature: " + e.getMessage());
        }
    }

    @GetMapping("/downloadtemperature")
    public ResponseEntity<String> downloadTemperature() {
        try {
            String temperature = fileService.getTemperatureFromFile();

            return ResponseEntity
                .status(HttpStatus.OK)
                .body("{\"temperature\": " + temperature + "}");

        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Error trying to read temperature: " + e.getMessage());
        }
    }

    

    @GetMapping("/downloadsound")
    public ResponseEntity<String> downloadBase64() {
        try {
            String base64 = fileService.getBase64FromFile("sound.mp3");

            return ResponseEntity
                .status(HttpStatus.OK)
                .body("{\"base64\": \"" + base64 + "\"}");

        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Error trying to read file: " + e.getMessage());
        }
    }

    @PostMapping("/uploadsound")
    public ResponseEntity<String> uploadBase64(@RequestBody Base64Request base64Request) {
        try {
            fileService.saveBase64ToFile(base64Request.getBase64());

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("File saved successfully");

        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error trying to save file: " + e.getMessage());
        }
    }

    public static class Base64Request {
        private String base64;

        public String getBase64() {
            return base64;
        }

        public void setBase64(String base64) {
            this.base64 = base64;
        }
    }
}
