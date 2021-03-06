package com.amber.book.springboot.service.posts;

import com.amber.book.springboot.domain.posts.Posts;
import com.amber.book.springboot.domain.posts.PostsRepository;
import com.amber.book.springboot.web.dto.PostsListResponseDto;
import com.amber.book.springboot.web.dto.PostsResponseDto;
import com.amber.book.springboot.web.dto.PostsSaveRequestDto;
import com.amber.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId(); //요청 시 전달받은 Dto 객체를 builder().~.build()를 통해 테이블에 들어갈 형태로 만든 후 save()
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts=postsRepository.findById(id).orElseThrow((()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id)));
        //데이터베이스에서 해당 id의 데이터 조회, 없으면 예외처리

        posts.update(requestDto.getTitle(),requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) //readOnly=true;트랜잭션 범위는 유지하되 조회 기능만 남겨두어 조회 속도 개선
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
        //.map(PostsListResponseDto::new) == .map(posts->new PostsListResponseDto(posts))
        //postsRepository 결과로 넘어온 Posts의 Stream을 map을통해 PostsListResponseDto로 변환한 후 리스트로 반환하도록 함
    }

    @Transactional
    public void delete(Long id){
        Posts posts=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        postsRepository.delete(posts);
    }
}
