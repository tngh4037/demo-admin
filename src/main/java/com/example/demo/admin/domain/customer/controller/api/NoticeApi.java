package com.example.demo.admin.domain.customer.controller.api;

import com.example.demo.admin.domain.customer.service.NoticeFileService;
import com.example.demo.admin.global.common.JsonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/customer/notices")
@RequiredArgsConstructor
public class NoticeApi {

    private final NoticeFileService noticeFileService;

    @PostMapping("/{noticeNo}/files/{noticeFileNo}/remove")
    public JsonResult<?> remove(@PathVariable("noticeNo") Integer noticeNo,
                                @PathVariable("noticeFileNo") Integer noticeFileNo) {
        noticeFileService.removeFile(noticeNo, noticeFileNo);
        return JsonResult.ok();
    }
}
