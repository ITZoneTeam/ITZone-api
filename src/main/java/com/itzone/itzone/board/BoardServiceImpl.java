package com.itzone.itzone.board;

import com.itzone.itzone.awsS3.S3File;
import com.itzone.itzone.common.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    /**
     * 게시글 생성
     *
     * @AuthenticationPrincipal oAuth User 필요
     * Category ID에 해당하는 category Object 필요
     * S3Files 저장할 AWS S3 Service 필요
     *
     * @param requestDto 게시글 작성 요청 데이터
     * @return 요청 처리 결과
     */
    @Override
    public ApiResponseDto createBoard(BoardRequestDto requestDto) {
        Board board = Board.builder()
                        //.user()
                        .title(requestDto.getTitle())
                        .content(requestDto.getContent())
                        //.bbc(requestDto.getCategory())
                        .build();

        boardRepository.save(board);

        return new ApiResponseDto("게시글 등록 성공", HttpStatus.CREATED.value());
    }

    /**
     * 게시글 전체 조회
     *
     * @return 요청 처리 결과
     */
    @Transactional
    @Override
    public Page<BoardResponseDto> readBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Board> boardList = boardRepository.findAll(pageable);

        return boardList.map(BoardResponseDto::new);
    }


    /**
     * 게시글 단일 조회
     *
     * @param id 게시글 id
     * @return 요청 처리 결과
     */
    @Override
    public BoardResponseDto readBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));

        return new BoardResponseDto(board);
    }
}
