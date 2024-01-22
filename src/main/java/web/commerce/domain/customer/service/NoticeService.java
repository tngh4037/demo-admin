package web.commerce.domain.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.commerce.domain.customer.domain.Notice;
import web.commerce.domain.customer.repository.NoticeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> getList() {
        return noticeRepository.getList();
    }

    public Notice getDetail(Integer noticeNo) {
        return noticeRepository.getDetail(noticeNo);
    }

    public void register(Notice notice) {
        noticeRepository.register(notice);
    }
}
