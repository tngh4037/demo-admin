package web.commerce.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.commerce.customer.domain.Notice;
import web.commerce.customer.repository.NoticeRepository;

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
