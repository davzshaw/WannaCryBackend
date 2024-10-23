package net.davzshaw.wannacry.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class FileService {

    private final String STORAGE_DIR = "src/main/java/net/davzshaw/wannacry/storage/";

    public void clearFile() throws IOException {
        File storageDir = new File(STORAGE_DIR);
        if (storageDir.exists() && storageDir.isDirectory()) {
            for (File file : storageDir.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
        }
    }

    public String getTemperatureFromFile() throws IOException {
        File file = new File(STORAGE_DIR + "temperature.txt");
        if (!file.exists()) {
            throw new IOException("File not found: " + file.getAbsolutePath());
        }

        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileContent);
        }

        return new String(fileContent);
    }

    public void saveTemperature(String temperature) throws IOException {
        File storageDir = new File(STORAGE_DIR);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(STORAGE_DIR + "temperature.txt")) {
            fos.write(temperature.toString().getBytes());
        }
    }

    public void saveBase64ToFile(String base64Text) throws IOException {

        byte[] data = Base64.getDecoder().decode(base64Text);

        File storageDir = new File(STORAGE_DIR);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(STORAGE_DIR + "sound.mp3")) {
            fos.write(data);
        }
    }

    public String getBase64FromFile(String fileName) throws IOException {
        File file = new File(STORAGE_DIR + fileName);
        if (!file.exists()) {
            throw new IOException("File not found: " + file.getAbsolutePath());
        }

        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileContent);
        }

        return Base64.getEncoder().encodeToString(fileContent);
    }
}
