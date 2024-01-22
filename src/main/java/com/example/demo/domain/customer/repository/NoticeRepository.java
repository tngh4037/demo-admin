package com.example.demo.domain.customer.repository;

import com.example.demo.domain.customer.domain.Notice;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class NoticeRepository {

    private static final Map<Integer, Notice> store = new HashMap<>();
    private static int SEQUENCE = 1;

    public List<Notice> getList() {
        return new ArrayList<>(store.values());
    }

    public Notice getDetail(Integer noticeNo) {
        return store.get(noticeNo);
    }

    public void register(Notice notice) {
        notice.setNoticeNo(SEQUENCE++);
        store.put(notice.getNoticeNo(), notice);
    }

    public void clear() {
        store.clear();
    }
}
