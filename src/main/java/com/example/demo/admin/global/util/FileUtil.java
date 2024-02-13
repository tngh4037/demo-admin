package com.example.demo.admin.global.util;

import com.example.demo.admin.global.common.UploadFile;
import com.example.demo.admin.global.error.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUtil {

    /**
     * upload files
     */
    public static List<UploadFile> uploadFiles(List<MultipartFile> multipartFiles, String fileDir) {
        List<UploadFile> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            files.add(uploadFile(multipartFile, fileDir));
        }
        return files;
    }

    /**
     * upload file
     */
    public static UploadFile uploadFile(MultipartFile multipartFile, String fileDir) {
        if (multipartFile.isEmpty()) {
            throw new FileUploadException("file is empty");
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String fullPath = fileDir + storeFileName;

        try {
            makeDirectory(fileDir);
            multipartFile.transferTo(new File(fullPath));
        } catch (IOException | IllegalStateException e) {
            throw new FileUploadException("file upload failed", e);
        }

        return new UploadFile(originalFilename, storeFileName, fullPath);
    }

    /**
     * make directory
     */
    private static void makeDirectory(String fileDir) {
        File folder = new File(fileDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    /**
     * delete file
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    /**
     * create fileName for storage
     */
    private static String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString().replace("-", "");;
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    /**
     * extract file extension
     */
    private static String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
