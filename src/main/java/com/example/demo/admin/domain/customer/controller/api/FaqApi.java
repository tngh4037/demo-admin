package com.example.demo.admin.domain.customer.controller.api;

import com.example.demo.admin.domain.customer.dto.FaqAddDto;
import com.example.demo.admin.domain.customer.dto.FaqEditDto;
import com.example.demo.admin.domain.customer.service.FaqService;
import com.example.demo.admin.global.common.JsonResult;
import com.example.demo.admin.global.common.ValidationSequence;
import com.example.demo.admin.global.util.PhotoUtil;
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
    private final PhotoUtil photoUtil;

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
        String uploadPath = photoUtil.ckUpload(request);
        mav.addObject("uploaded", true);
        mav.addObject("url", uploadPath);
        return mav;
    }
}
