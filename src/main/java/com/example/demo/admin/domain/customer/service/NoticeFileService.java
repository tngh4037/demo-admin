package com.example.demo.admin.domain.customer.service;

import com.example.demo.admin.domain.customer.domain.NoticeFile;
import com.example.demo.admin.domain.customer.exception.NoticeFileNotFoundException;
import com.example.demo.admin.domain.customer.repository.NoticeFileRepository;
import com.example.demo.admin.global.common.UploadFile;
import com.example.demo.admin.global.common.define.FileUploadType;
import com.example.demo.admin.global.util.CommonUtil;
import com.example.demo.admin.global.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeFileService {

    private final NoticeFileRepository noticeFileRepository;

    public List<NoticeFile> findItems(Integer noticeNo) {
        return noticeFileRepository.findAll(noticeNo);
    }

    public NoticeFile findItem(Integer noticeNo, Integer noticeFileNo) {
        return findItems(noticeNo).stream()
                .filter(s -> s.getNoticeFileNo().equals(noticeFileNo))
                .findFirst()
                .orElseThrow(NoticeFileNotFoundException::new);
    }

    public void saveFiles(Integer noticeNo, List<MultipartFile> uploadFiles) {
        if (FileUtil.isEmpty(uploadFiles)) return;

        FileUtil.checkValidate(uploadFiles, FileUploadType.CUSTOMER_NOTICE);
        uploadFiles(noticeNo, uploadFiles);
    }

    public void updateFiles(Integer noticeNo, List<MultipartFile> uploadFiles) {
        FileUtil.checkValidate(uploadFiles, FileUploadType.CUSTOMER_NOTICE,
                findItems(noticeNo).stream().map(NoticeFile::getFileName).toList());

        if (!FileUtil.isEmpty(uploadFiles)) {
            uploadFiles(noticeNo, uploadFiles);
        }
    }

    private void uploadFiles(Integer noticeNo, List<MultipartFile> uploadFiles) {
        List<UploadFile> uploadedFiles = null;
        try {
            uploadedFiles = FileUtil.uploadFiles(uploadFiles, FileUploadType.CUSTOMER_NOTICE);
            for (UploadFile result : uploadedFiles) {
                noticeFileRepository.save(NoticeFile.of()
                        .noticeNo(noticeNo)
                        .fileName(result.getStoreFileName())
                        .orgFileName(result.getOriginalFilename())
                        .build());
            }
        } catch (RuntimeException e) {
            log.error("notice uploadFiles failed", e);
            if (!CommonUtil.isEmpty(uploadedFiles)) {
                uploadedFiles.forEach(v -> FileUtil.deleteFile(v.getStorePath()));
            }
            throw e;
        }
    }

    public void removeFiles(Integer noticeNo) {
        List<NoticeFile> noticeFiles = findItems(noticeNo);
        noticeFiles.forEach(v -> FileUtil.deleteFile(FileUploadType.CUSTOMER_NOTICE.getStorePath(v.getFileName())));
        noticeFileRepository.delete(noticeNo);
    }

    public void removeFile(Integer noticeNo, Integer noticeFileNo) {
        NoticeFile noticeFile = findItem(noticeNo, noticeFileNo);
        FileUtil.deleteFile(FileUploadType.CUSTOMER_NOTICE.getStorePath(noticeFile.getFileName()));
        noticeFileRepository.deleteById(noticeFileNo);
    }
}
