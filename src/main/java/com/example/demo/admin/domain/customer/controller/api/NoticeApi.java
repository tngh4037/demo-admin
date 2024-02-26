package com.example.demo.admin.domain.customer.controller.api;

import com.example.demo.admin.domain.customer.service.NoticeFileService;
import com.example.demo.admin.domain.customer.service.NoticeService;
import com.example.demo.admin.global.common.JsonResult;
import com.example.demo.admin.global.common.define.Yn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/customer/notices")
@RequiredArgsConstructor
public class NoticeApi {

    private final NoticeService noticeService;
    private final NoticeFileService noticeFileService;

    @PostMapping("/{noticeNo}/edit/display")
    public JsonResult<?> editDisplay(@PathVariable("noticeNo") Integer noticeNo,
                                     @RequestBody String displayYn) {
        noticeService.updateDisplayYn(noticeNo, Yn.findByCode(displayYn));
        return JsonResult.ok();
    }

    @PostMapping("/{noticeNo}/files/{noticeFileNo}/remove")
    public JsonResult<?> remove(@PathVariable("noticeNo") Integer noticeNo,
                                @PathVariable("noticeFileNo") Integer noticeFileNo) {
        noticeFileService.removeFile(noticeNo, noticeFileNo);
        return JsonResult.ok();
    }
}
