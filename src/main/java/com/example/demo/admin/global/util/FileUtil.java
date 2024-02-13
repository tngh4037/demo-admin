package com.example.demo.admin.global.util;

import com.example.demo.admin.global.common.UploadFile;
import com.example.demo.admin.global.common.define.FileUploadType;
import com.example.demo.admin.global.error.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

public class FileUtil {

    /**
     * upload files
     */
    public static List<UploadFile> uploadFiles(List<MultipartFile> multipartFiles, FileUploadType fileUploadType) {
        if (multipartFiles.isEmpty()) {
            throw new FileUploadException("files is empty");
        }

        checkValidate(multipartFiles, fileUploadType);

        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String originalFilename = Objects.requireNonNull(multipartFile.getOriginalFilename());
            String uploadFileName = createUploadFileName(originalFilename);
            String uploadPath = fileUploadType.getUploadPath(uploadFileName);

            try {
                makeDirectory(fileUploadType.getFileDir());
                multipartFile.transferTo(new File(uploadPath));
            } catch (IOException | IllegalStateException e) {
                throw new FileUploadException("file upload failed", e);
            }

            uploadFiles.add(new UploadFile(originalFilename, uploadFileName, uploadPath));
        }

        return uploadFiles;
    }

    /**
     * upload file
     */
    public static UploadFile uploadFile(MultipartFile multipartFile, FileUploadType fileUploadType) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new FileUploadException("file is empty");
        }

        return uploadFiles(List.of(multipartFile), fileUploadType).get(0);
    }

    /**
     * file validation
     */
    private static void checkValidate(List<MultipartFile> multipartFiles, FileUploadType fileUploadType) {
        long size = 0L;
        for (MultipartFile multipartFile : multipartFiles) {
            checkExt(multipartFile, fileUploadType);
            size += multipartFile.getSize();
        }
        if (size > fileUploadType.getMaxSize()) {
            throw new FileUploadException("file size exceeds maxSize : " + size);
        }
        if (multipartFiles.size() > fileUploadType.getMaxCount()) {
            throw new FileUploadException("file count exceeds maxCount : " + multipartFiles.size());
        }
    }

    /**
     * check extension
     */
    private static void checkExt(MultipartFile file, FileUploadType fileUploadType) {
        if (fileUploadType.getExtensions() == null) return;

        String fileExt = extractExt(Objects.requireNonNull(file.getOriginalFilename()));
        for (String ext : fileUploadType.getExtensions()) {
            if (ext.equals(extractExt(fileExt))) {
                return;
            }
        }

        throw new FileUploadException("file extension is invalid : " + fileExt);
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
    private static String createUploadFileName(String originalFilename) {
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
