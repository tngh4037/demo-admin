package com.example.demo.admin.domain.customer.controller;

import com.example.demo.admin.domain.customer.domain.NoticeFile;
import com.example.demo.admin.domain.customer.exception.NoticeDuplicateException;
import com.example.demo.admin.domain.customer.service.NoticeFileService;
import com.example.demo.admin.global.common.constant.PageConstant;
import com.example.demo.admin.global.common.constant.ViewConstant;
import com.example.demo.admin.domain.customer.domain.Notice;
import com.example.demo.admin.domain.customer.dto.NoticeAddDto;
import com.example.demo.admin.domain.customer.dto.NoticeEditDto;
import com.example.demo.admin.domain.customer.dto.NoticeSearchDto;
import com.example.demo.admin.domain.customer.service.NoticeService;
import com.example.demo.admin.domain.customer.validator.NoticeSearchValidator;
import com.example.demo.admin.global.common.define.FileUploadType;
import com.example.demo.admin.global.error.exception.UploadFileException;
import com.example.demo.admin.global.util.ErrorUtil;
import com.example.demo.admin.global.common.PaginationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * PRG(Post-Redirect-Get) pattern
 */
@Slf4j
@Controller
@RequestMapping("/customer/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final NoticeFileService noticeFileService;
    private final NoticeSearchValidator noticeSearchValidator;

    @GetMapping
    public String list(@Validated @ModelAttribute("searchDto") NoticeSearchDto noticeSearchDto,
                       BindingResult bindingResult,
                       Model model) {

        noticeSearchValidator.validate(noticeSearchDto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            model.addAttribute("msg", ErrorUtil.getBindingMessage(bindingResult));
            model.addAttribute("url", "/customer/notices");
            return ViewConstant.COMMON_REDIRECT;
        }

        PaginationDto paginationDto = new PaginationDto(noticeSearchDto.getPageNo(),
                PageConstant.COMMON_RECORD_COUNT, PageConstant.COMMON_PAGE_SIZE);
        List<Notice> noticeList = noticeService.findItems(noticeSearchDto, paginationDto);

        model.addAttribute("totalCount", paginationDto.getTotalRecordCount());
        model.addAttribute("paginationDto", paginationDto);
        model.addAttribute("noticeList", noticeList);

        return ViewConstant.CUSTOMER_NOTICE_LIST;
    }

    @GetMapping("/{noticeNo}")
    public String detailForm(@PathVariable("noticeNo") Integer noticeNo, Model model) {
        model.addAttribute("notice", noticeService.findById(noticeNo));
        model.addAttribute("noticeFiles", noticeFileService.findItems(noticeNo));
        return ViewConstant.CUSTOMER_NOTICE_DETAIL;
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("noticeAddDto", NoticeAddDto.initForm());
        return ViewConstant.CUSTOMER_NOTICE_ADD_FORM;
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("noticeAddDto") NoticeAddDto noticeAddDto,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return ViewConstant.CUSTOMER_NOTICE_ADD_FORM;
        }

        try {
            Notice notice = noticeService.save(noticeAddDto);
            redirectAttributes.addAttribute("noticeNo", notice.getNoticeNo());
        } catch (NoticeDuplicateException ex) {
            bindingResult.addError(new FieldError("noticeAddDto", "title", noticeAddDto.getTitle(), false, null, null, ex.getMessage()));
        } catch (UploadFileException ex) {
            bindingResult.addError(new FieldError("noticeAddDto", "uploadFiles", ex.getMessage()));
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return ViewConstant.CUSTOMER_NOTICE_ADD_FORM;
        }

        return "redirect:/customer/notices/{noticeNo}";
    }

    @GetMapping("/{noticeNo}/edit")
    public String editForm(@PathVariable("noticeNo") Integer noticeNo, Model model) {
        model.addAttribute("noticeEditDto", NoticeEditDto.initForm(noticeService.findById(noticeNo)));
        model.addAttribute("noticeFiles", noticeFileService.findItems(noticeNo));
        return ViewConstant.CUSTOMER_NOTICE_EDIT_FORM;
    }

    @PostMapping("/{noticeNo}/edit")
    public String edit(@PathVariable("noticeNo") Integer noticeNo,
                       @Validated @ModelAttribute("noticeEditDto") NoticeEditDto noticeEditDto,
                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            model.addAttribute("noticeFiles", noticeFileService.findItems(noticeNo));
            return ViewConstant.CUSTOMER_NOTICE_EDIT_FORM;
        }

        try {
            noticeService.update(noticeNo, noticeEditDto);
        } catch (NoticeDuplicateException ex) {
            bindingResult.addError(new FieldError("noticeEditDto", "title", noticeEditDto.getTitle(), false, null, null, ex.getMessage()));
        } catch (UploadFileException ex) {
            bindingResult.addError(new FieldError("noticeEditDto", "uploadFiles", ex.getMessage()));
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            model.addAttribute("noticeFiles", noticeFileService.findItems(noticeNo));
            return ViewConstant.CUSTOMER_NOTICE_EDIT_FORM;
        }

        return "redirect:/customer/notices/{noticeNo}";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("noticeNos") Integer[] noticeNos,
                         @RequestParam(value = "searchParams", required = false, defaultValue = "") String searchParams) {
        noticeService.remove(noticeNos);
        return "redirect:/customer/notices" + searchParams;
    }

    @GetMapping("/{noticeNo}/files/{noticeFileNo}/download")
    public ResponseEntity<Resource> download(@PathVariable("noticeNo") Integer noticeNo,
                                             @PathVariable("noticeFileNo") Integer noticeFileNo) throws MalformedURLException {
        NoticeFile noticeFile = noticeFileService.findItem(noticeNo, noticeFileNo);
        UrlResource resource = new UrlResource("file:" + FileUploadType.CUSTOMER_NOTICE.getStorePath(noticeFile.getFileName()));

        String encodedStoreFileName = UriUtils.encode(noticeFile.getOrgFileName(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedStoreFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}