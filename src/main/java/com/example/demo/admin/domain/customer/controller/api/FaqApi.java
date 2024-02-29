package com.example.demo.admin.domain.customer.controller.api;

import com.example.demo.admin.domain.customer.dto.FaqAddDto;
import com.example.demo.admin.domain.customer.dto.FaqEditDto;
import com.example.demo.admin.domain.customer.service.FaqService;
import com.example.demo.admin.global.common.JsonResult;
import com.example.demo.admin.global.common.UploadFile;
import com.example.demo.admin.global.common.ValidationSequence;
import com.example.demo.admin.global.common.define.FileUploadType;
import com.example.demo.admin.global.error.exception.UploadFileException;
import com.example.demo.admin.global.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer/faqs")
@RequiredArgsConstructor
public class FaqApi {

    private final FaqService faqService;

    @PostMapping("/add")
    public JsonResult<?> add(@RequestBody @Validated(ValidationSequence.class) FaqAddDto faqAddDto) {
        faqService.save(faqAddDto);
        return JsonResult.ok();
    }

    @PostMapping("/{faqNo}/edit")
    public JsonResult<?> edit(@PathVariable("faqNo") Integer faqNo,
                              @RequestBody @Validated(ValidationSequence.class) FaqEditDto noticeEditDto) {
        faqService.update(faqNo, noticeEditDto);
        return JsonResult.ok();
    }

    @PostMapping("/remove")
    public JsonResult<?> remove(@RequestBody List<Integer> faqNos) {
        faqService.remove(faqNos);
        return JsonResult.ok();
    }

    /**
     * editor 에서 이미지 업로드 시 최종 저장 전 까지는 temp directory 에 저장 한다.
     * TODO :: fileUpload 경로 등 재점검 할 것 ( FileUploadType )
     */
    @PostMapping("/images/upload")
    public ModelAndView upload(MultipartHttpServletRequest request) {
        ModelAndView mav = new ModelAndView("jsonView");
        UploadFile uploadFile;
        try {
            uploadFile = FileUtil.uploadFile(request.getFile("upload"), FileUploadType.CUSTOMER_TEMP);
            mav.addObject("uploaded", true);
            mav.addObject("url", "/DemoResource/customer/temp/" + uploadFile.getStoreFileName());
        } catch (UploadFileException e) {
            log.error("faq file upload error", e);
            mav.addObject("uploaded", false);
            mav.addObject("url", "");
        }
        return mav;
    }
}
