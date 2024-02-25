package com.example.demo.admin.domain.customer.controller.api;

import com.example.demo.admin.domain.customer.dto.FaqAddDto;
import com.example.demo.admin.domain.customer.dto.FaqEditDto;
import com.example.demo.admin.domain.customer.service.FaqService;
import com.example.demo.admin.global.common.JsonResult;
import com.example.demo.admin.global.common.UploadFile;
import com.example.demo.admin.global.common.ValidationSequence;
import com.example.demo.admin.global.common.define.FileUploadType;
import com.example.demo.admin.global.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.net.MalformedURLException;
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

    @PostMapping("/images/upload")
    public ModelAndView upload(MultipartHttpServletRequest request) {
        ModelAndView mav = new ModelAndView("jsonView");
        UploadFile uploadFile = FileUtil.uploadFile(request.getFile("upload"), FileUploadType.CUSTOMER_FAQ);
        mav.addObject("uploaded", true);
        mav.addObject("url", "/customer/faqs/images/" + uploadFile.getStoreFileName());
        return mav;
    }

    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {
        return new UrlResource("file:" + FileUploadType.CUSTOMER_FAQ.getFileDir() + filename);
    }
}
