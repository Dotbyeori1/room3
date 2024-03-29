package org.ezone.room.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.ezone.room.dto.QnaReplyDTO;
import org.ezone.room.dto.ReviewReplyDTO;
import org.ezone.room.entity.Member;
import org.ezone.room.entity.QnaReply;
import org.ezone.room.entity.ReviewBoard;
import org.ezone.room.entity.ReviewReply;
import org.ezone.room.repository.MemberRepository;
import org.ezone.room.repository.ReviewBoardRepository;
import org.ezone.room.repository.ReviewReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewReplyServiceImpl implements ReviewReplyService {

    private final ReviewReplyRepository reviewReplyRepository;
    private final ReviewBoardRepository reviewBoardRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long register(ReviewReplyDTO reviewReplyDTO, Member member) {
        reviewReplyDTO.setReplyer(member.getEmail());
        System.out.println(reviewReplyDTO);
        ReviewReply reviewReply = dtoToEntity(reviewReplyDTO, reviewBoardRepository,memberRepository);

        reviewReplyRepository.save(reviewReply);

        return reviewReply.getRrno();
    }

    public List<ReviewReplyDTO> getList(Long rbno) {
        List<ReviewReply> result = reviewReplyRepository.getRepliesByReviewBoardOrderByRrno(ReviewBoard.builder().rbno(rbno).build());
        return result.stream()
                .flatMap(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void modify(ReviewReplyDTO reviewReplyDTO) {

        ReviewReply reviewReply = dtoToEntity(reviewReplyDTO, reviewBoardRepository, memberRepository);

        reviewReplyRepository.save(reviewReply);
    }

    @Override
    public void remove(Long rno) {
        reviewReplyRepository.deleteById(rno);
    }
}
