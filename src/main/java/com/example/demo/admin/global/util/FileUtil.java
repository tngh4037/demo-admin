package com.example.demo.admin.global.util;

import com.example.demo.admin.global.common.UploadFile;
import com.example.demo.admin.global.common.define.FileUploadType;
import com.example.demo.admin.global.error.exception.UploadFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

public class FileUtil {

    /**
     * upload files
     */
    public static List<UploadFile> uploadFiles(List<MultipartFile> uploadFiles, FileUploadType fileUploadType) {
        if (isEmpty(uploadFiles)) {
            throw new UploadFileException("파일이 존재하지 않습니다.");
        }

        List<UploadFile> result = new ArrayList<>();
        for (MultipartFile uploadFile : uploadFiles) {
            String originalFilename = Objects.requireNonNull(uploadFile.getOriginalFilename());
            String storeFileName = createStoreFileName(originalFilename);
            String storePath = fileUploadType.getStorePath(storeFileName);

            try {
                createDirectory(fileUploadType.getFileDir());
                uploadFile.transferTo(new File(storePath));
            } catch (IOException | IllegalStateException e) {
                throw new UploadFileException(e);
            }

            result.add(new UploadFile(originalFilename, storeFileName, storePath));
        }

        return result;
    }

    /**
     * upload file
     */
    public static UploadFile uploadFile(MultipartFile uploadFile, FileUploadType fileUploadType) {
        return uploadFiles(List.of(uploadFile), fileUploadType).get(0);
    }

    /**
     * file validation
     */
    public static void checkValidate(List<MultipartFile> uploadFiles, FileUploadType fileUploadType) {
        if (isEmpty(uploadFiles)) return;

        long totalFileSize = 0L;
        int totalFileCount = 0;

        // 새로 첨부할 파일이 정책과 맞는지 검증
        for (MultipartFile uploadFile : uploadFiles) {
            checkExt(uploadFile.getOriginalFilename(), fileUploadType);
            totalFileSize += uploadFile.getSize();
        }
        if (totalFileSize > fileUploadType.getMaxSize()) {
            throw new UploadFileException("첨부 가능한 파일 용량을 초과했습니다.");
        }
        totalFileCount = uploadFiles.size();
        if (totalFileCount > fileUploadType.getMaxCount()) {
            throw new UploadFileException("첨부 가능한 파일 개수를 초과했습니다.");
        }
    }

    /**
     * file validation with orgUploadedFiles
     */
    public static void checkValidate(List<MultipartFile> uploadFiles,
                                     FileUploadType fileUploadType, List<String> orgUploadedFiles) {
        long totalFileSize = 0L;
        int totalFileCount = 0;

        // 기존 첨부된 파일이 현재 정책과 맞는지 검증
        if (!CommonUtil.isEmpty(orgUploadedFiles)) {
            for (String orgUploadedFile : orgUploadedFiles) {
                checkExt(orgUploadedFile, fileUploadType);
                totalFileSize += new File(fileUploadType.getStorePath(orgUploadedFile)).length();
            }
            if (totalFileSize > fileUploadType.getMaxSize()) {
                throw new UploadFileException("첨부 가능한 파일 용량을 초과했습니다.");
            }
            totalFileCount = orgUploadedFiles.size();
            if (totalFileCount > fileUploadType.getMaxCount()) {
                throw new UploadFileException("첨부 가능한 파일 개수를 초과했습니다.");
            }
        }

        if (isEmpty(uploadFiles)) return;

        // 새로 첨부할 파일이 정책과 맞는지 검증
        for (MultipartFile uploadFile : uploadFiles) {
            checkExt(uploadFile.getOriginalFilename(), fileUploadType);
            totalFileSize += uploadFile.getSize();
        }
        if (totalFileSize > fileUploadType.getMaxSize()) {
            throw new UploadFileException("첨부 가능한 파일 용량을 초과했습니다.");
        }
        totalFileCount += uploadFiles.size();
        if (totalFileCount > fileUploadType.getMaxCount()) {
            throw new UploadFileException("첨부 가능한 파일 개수를 초과했습니다.");
        }
    }

    /**
     * check extension
     */
    private static void checkExt(String fileName, FileUploadType fileUploadType) {
        String fileExt = extractExt(fileName);
        for (String ext : fileUploadType.getExtensions()) {
            if (ext.equals(fileExt)) {
                return;
            }
        }
        throw new UploadFileException("첨부 가능한 파일 확장자가 아닙니다.");
    }

    /**
     * create directory
     */
    private static void createDirectory(String fileDir) {
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
     * create storeFileName
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

    /**
     * check file empty
     */
    public static boolean isEmpty(MultipartFile file) {
        if (file == null) {
            return true;
        }

        return file.isEmpty();
    }

    /**
     * check files empty
     */
    public static boolean isEmpty(List<MultipartFile> files) {
        boolean result = false;

        if (files == null || files.isEmpty()) {
            return true;
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                result = true;
                break;
            }
        }
        return result;
    }
}
