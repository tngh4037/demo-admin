package com.example.demo.admin.domain.customer.service;

import com.example.demo.admin.domain.customer.domain.NoticeFile;
import com.example.demo.admin.domain.customer.exception.NoticeFileException;
import com.example.demo.admin.domain.customer.repository.NoticeFileRepository;
import com.example.demo.admin.global.common.UploadFile;
import com.example.demo.admin.global.common.define.FileUploadType;
import com.example.demo.admin.global.error.exception.DataNotFoundException;
import com.example.demo.admin.global.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
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
                .orElseThrow(DataNotFoundException::new);
    }

    public void saveFiles(Integer noticeNo, List< MultipartFile > multipartFiles) {
        if (multipartFiles == null) return;

        List<UploadFile> uploadFiles = null;
        try {
            uploadFiles = FileUtil.uploadFiles(multipartFiles, FileUploadType.CUSTOMER_NOTICE);
            for (UploadFile uploadFile : uploadFiles) {
                noticeFileRepository.save(NoticeFile.of()
                        .noticeNo(noticeNo)
                        .fileName(uploadFile.getUploadFileName())
                        .orgFileName(uploadFile.getOriginalFilename())
                        .build());
            }
        } catch (RuntimeException e) {
            log.error("notice saveFiles failed", e);
            if (!ObjectUtils.isEmpty(uploadFiles)) {
                uploadFiles.forEach(v -> FileUtil.deleteFile(v.getUploadPath()));
            }
            throw new NoticeFileException(e);
        }
    }

    public void removeFiles(Integer noticeNo) {
        List<NoticeFile> noticeFiles = findItems(noticeNo);
        noticeFiles.forEach(v -> FileUtil.deleteFile(FileUploadType.CUSTOMER_NOTICE.getUploadPath(v.getFileName())));
        noticeFileRepository.delete(noticeNo);
    }

    public void removeFile(Integer noticeNo, Integer noticeFileNo) {
        NoticeFile noticeFile = findItem(noticeNo, noticeFileNo);
        FileUtil.deleteFile(FileUploadType.CUSTOMER_NOTICE.getUploadPath(noticeFile.getFileName()));
        noticeFileRepository.deleteById(noticeFileNo);
    }
}
