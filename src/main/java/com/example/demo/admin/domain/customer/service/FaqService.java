package com.example.demo.admin.domain.customer.service;

import com.example.demo.admin.domain.customer.define.FaqType;
import com.example.demo.admin.domain.customer.domain.Faq;
import com.example.demo.admin.domain.customer.dto.FaqAddDto;
import com.example.demo.admin.domain.customer.dto.FaqEditDto;
import com.example.demo.admin.domain.customer.dto.FaqSearchDto;
import com.example.demo.admin.domain.customer.exception.FaqFileException;
import com.example.demo.admin.domain.customer.exception.FaqNotFoundException;
import com.example.demo.admin.domain.customer.exception.FaqPolicyException;
import com.example.demo.admin.domain.customer.repository.FaqRepository;
import com.example.demo.admin.global.common.define.Yn;
import com.example.demo.admin.global.common.PaginationDto;
import com.example.demo.admin.global.error.exception.UploadFileException;
import com.example.demo.admin.global.util.FileUtil;
import com.example.demo.admin.global.util.ImageUtil;
import com.example.demo.admin.global.util.MessageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaqService {

    private static final int ACTIVE_DISPLAY_TOP_MAX_COUNT = 3;

    private final FaqRepository faqRepository;

    public List<Faq> findItems(FaqSearchDto faqSearchDto, PaginationDto paginationDto) {
        List<Faq> faqs = new ArrayList<>();
        int count = faqRepository.count(faqSearchDto);
        if (count > 0) {
            paginationDto.setTotalRecordCount(count);
            faqs = faqRepository.findAll(faqSearchDto, paginationDto);
        }

        return faqs;
    }

    public Faq findById(Integer faqNo) {
        return faqRepository.findById(faqNo).orElseThrow(FaqNotFoundException::new);
    }

    public Faq save(FaqAddDto faqAddDto) {
        // validation
        checkActiveDisplayTopMaxCount(null, faqAddDto.getDisplayTopYn(), faqAddDto.getFaqType());

        // editor image file upload
        String newAnswer = fileCopies(faqAddDto.getAnswer());
        faqAddDto.setAnswer(newAnswer);

        // save
        return faqRepository.save(faqAddDto.toEntity());
    }

    public void update(Integer faqNo, FaqEditDto faqEditDto) {
        // validation
        Faq faq = findById(faqNo);
        checkActiveDisplayTopMaxCount(faqNo, faqEditDto.getDisplayTopYn(), faqEditDto.getFaqType());

        // editor image file upload
        String newAnswer = fileCopies(faqEditDto.getAnswer());
        faqEditDto.setAnswer(newAnswer);

        // update
        faqRepository.update(faqNo, faqEditDto.toEntity());

        // editor old image file delete
        removeFilesByEdit(faq.getAnswer(), faqEditDto.getAnswer());
    }

    @Transactional
    public void remove(List<Integer> faqNos) {
        for (Integer faqNo : faqNos) {
            Faq faq = findById(faqNo);
            removeFiles(faq.getAnswer());
            faqRepository.deleteById(faqNo);
        }
    }

    private void checkActiveDisplayTopMaxCount(Integer faqNo, Yn displayTopYn, FaqType faqType) {
        if (displayTopYn == Yn.NO) return;

        int count = faqRepository.countForActiveDisplayTop(faqNo, faqType);
        if (count >= ACTIVE_DISPLAY_TOP_MAX_COUNT) {
            throw new FaqPolicyException(MessageHelper.getMessage("customer.faq.invalid.active.display.max.count",
                    new Object[]{ACTIVE_DISPLAY_TOP_MAX_COUNT}));
        }
    }

    /**
     * (editor 에서 파일 첨부 후 최종 저장시) temp directory -> 실제 저장 경로로 파일을 copy 한다.
     * - 참고) temp directory 내부 파일은 주기 별 삭제 한다. (ex. 파일 생성 일자로 부터 3일이 경과된 시점에 batch 삭제 처리)
     */
    private String fileCopies(String answer) {
        List<String> srcPath = ImageUtil.getSrcPath(answer);
        for (String path : srcPath) {
            try {
                Path copyPath = FileUtil.copyFile(path, "/DemoResource/customer/faq/");
                answer = answer.replace(path, copyPath.toString());
            } catch (UploadFileException e) {
                log.error("faq file copy error", e);
                throw new FaqFileException(e);
            }
        }
        return answer;
    }

    /**
     * 게시글 삭제 시, 해당 글에 업로드 된 파일이 있다면 삭제 한다.
     */
    private void removeFiles(String answer) {
        List<String> srcPath = ImageUtil.getSrcPath(answer);
        srcPath.forEach(FileUtil::deleteFile);
    }

    /**
     * 게시글 수정 시, 기존 업로드 한 파일을 지우고 저장 했다면, 실제 저장 경로 에서 해당 파일을 삭제 한다.
     */
    private void removeFilesByEdit(String oldAnswer, String newAnswer) {
        List<String> oldPath = ImageUtil.getSrcPath(oldAnswer);
        List<String> newPath = ImageUtil.getSrcPath(newAnswer);
        oldPath.stream().filter(path -> !newPath.contains(path)).forEach(FileUtil::deleteFile);
    }
}