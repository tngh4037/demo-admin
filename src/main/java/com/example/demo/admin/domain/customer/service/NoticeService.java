package com.example.demo.admin.domain.customer.service;

import com.example.demo.admin.domain.customer.domain.NoticeFile;
import com.example.demo.admin.domain.customer.dto.NoticeAddDto;
import com.example.demo.admin.domain.customer.dto.NoticeEditDto;
import com.example.demo.admin.domain.customer.dto.NoticeSearchDto;
import com.example.demo.admin.domain.customer.exception.NoticeDuplicateException;
import com.example.demo.admin.domain.customer.exception.NoticeFileException;
import com.example.demo.admin.domain.customer.repository.NoticeFileRepository;
import com.example.demo.admin.domain.customer.repository.NoticeRepository;
import com.example.demo.admin.global.common.UploadFile;
import com.example.demo.admin.global.error.exception.DataNotFoundException;
import com.example.demo.admin.global.common.PaginationDto;
import com.example.demo.admin.global.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.demo.admin.domain.customer.domain.Notice;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;
    private final String fileDir;

    public NoticeService(NoticeRepository noticeRepository,
                         NoticeFileRepository noticeFileRepository,
                         final @Value("${file.dir.customer.notice}") String fileDir) {
        this.noticeRepository = noticeRepository;
        this.noticeFileRepository = noticeFileRepository;
        this.fileDir = fileDir;
    }

    public List<Notice> findItems(NoticeSearchDto noticeSearchDto, PaginationDto paginationDto) {
        List<Notice> notices = new ArrayList<>();
        int count = noticeRepository.count(noticeSearchDto);
        if (count > 0) {
            paginationDto.setTotalRecordCount(count);
            notices = noticeRepository.findAll(noticeSearchDto, paginationDto);
        }

        return notices;
    }

    public Notice findById(Integer noticeNo) {
        return noticeRepository.findById(noticeNo).orElseThrow(DataNotFoundException::new);
    }

    @Transactional
    public Notice save(NoticeAddDto noticeAddDto) {
        checkDuplicate(null, noticeAddDto.getTitle());
        Notice notice = noticeRepository.save(noticeAddDto.toEntity());
        uploadFiles(notice.getNoticeNo(), noticeAddDto.getFilesIfNotEmpty());
        return notice;
    }

    public void update(Integer noticeNo, NoticeEditDto noticeEditDto) {
        findById(noticeNo);
        checkDuplicate(noticeNo, noticeEditDto.getTitle());
        noticeRepository.update(noticeNo, noticeEditDto.toEntity());
    }

    private void checkDuplicate(Integer noticeNo, String title) {
        if (noticeRepository.isDuplicate(noticeNo, title)) {
            throw new NoticeDuplicateException();
        }
    }

    @Transactional
    public void remove(Integer[] noticeNos) {
        for (Integer noticeNo : noticeNos) {
            noticeRepository.deleteById(noticeNo);
        }
    }

    private void uploadFiles(Integer noticeNo, List<MultipartFile> multipartFiles) {
        if (multipartFiles == null) return;

        List<UploadFile> uploadFiles = null;
        try {
            uploadFiles = FileUtil.uploadFiles(multipartFiles, fileDir);
            for (UploadFile uploadFile : uploadFiles) {
                noticeFileRepository.save(NoticeFile.of()
                        .noticeNo(noticeNo)
                        .fileName(uploadFile.getStoreFileName())
                        .orgFileName(uploadFile.getOriginalFilename())
                        .build());
            }
        } catch (RuntimeException e) {
            log.error("notice uploadFiles failed", e);
            if (!ObjectUtils.isEmpty(uploadFiles)) {
                uploadFiles.forEach(v -> FileUtil.deleteFile(v.getFullPath()));
            }
            throw new NoticeFileException(e);
        }
    }
}
