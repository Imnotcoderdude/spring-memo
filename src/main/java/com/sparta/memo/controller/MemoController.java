package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MemoController {
    // 왜 이게 사용됐는지 모르는 부분, 제네릭 문법에 대해 공부하기. 키 밸류?
    private final Map<Long, Memo> memoList = new HashMap<>();

    //memo 생성하기 API 제작
    @PostMapping("/memos")
    public MemoResponseDto creatememo(@RequestBody MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        //Memo Max ID 찾아야함
        long MaxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(MaxId);

        // DB 저장
        memoList.put(memo.getId(), memo);

        // Entity -> ResponseDto
        MemoResponseDto memoresponseDto = new MemoResponseDto(memo);

        return memoresponseDto;
    }
    // 조회 하는 API
    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        // Map To List
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();

        return responseList;
    }
}
