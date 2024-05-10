package com.example.kubernetes.post.service;

import com.example.kubernetes.board.model.Board;
import com.example.kubernetes.board.repository.BoardRepository;
import com.example.kubernetes.common.component.Messenger;
import com.example.kubernetes.post.model.Post;
import com.example.kubernetes.post.model.PostDto;
import com.example.kubernetes.post.repository.PostRepository;
import com.example.kubernetes.user.model.User;
import com.example.kubernetes.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@CrossOrigin(origins = "*", allowedHeaders = "*") // http://localhost:3000 에서 온 AJAX요청만 받아주겠다는 의미.
@RestController
@Service
@RequiredArgsConstructor
@RequestMapping(path = "/api/posts")
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public Messenger save(PostDto dto) {
        User writer = userRepository.findById(dto.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("WriterId " + dto.getWriterId() + "not found."));
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("BoardId " + dto.getBoardId() + "not found."));

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .board(board)
                .writer(writer)
                .build();

        repository.save(post);

        System.out.println(" ============ BoardServiceImpl save instanceof =========== ");
        System.out.println((post instanceof Post) ? "SUCCESS" : "FAILURE");
        return Messenger.builder()
                .message((post instanceof Post) ? "SUCCESS" : "FAILURE")
                .id(post.getBoard().getId())
                .build();
    }

    @Transactional
    @Override
    public Messenger deleteById(Long id) {
        return Messenger.builder()
                .message(
                        Stream.of(id)
                                .filter(i -> existsById(i))
                                .peek(i -> repository.deleteById(i))
                                .map(i -> "SUCCESS")
                                .findAny()
                                .orElseGet(() -> "FAILURE")
                )
                .build();
    }

    @Override
    public List<PostDto> findAll() {
        return repository.findAllByOrderByIdDesc().stream().map(i -> entityToDto(i)).toList();
    }

    @Override
    public Optional<PostDto> findById(Long id) {
        return repository.findById(id).map(i -> entityToDto(i));
    }

    @Override
    public Messenger count() {
        return Messenger.builder()
                .message(repository.count() + "")
                .build();
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    @Override
    public Messenger modify(PostDto dto) {

        Optional<Post> post = repository.findById(dto.getId());
        if (post.isPresent()) {
            PostDto updatePost = dto.toBuilder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .build();

            repository.save(dtoToEntity(updatePost));

            return Messenger.builder()
                    .message("update SUCCESS")
                    .build();
        } else {
            log.warn("PostId '{}' not found.", dto.getId());
        }
        Post ent = repository.save(dtoToEntity(dto));
        System.out.println(" ============ BoardServiceImpl modify instanceof =========== ");
        System.out.println((ent instanceof Post) ? "SUCCESS" : "FAILURE");
        return Messenger.builder()
                .message((ent instanceof Post) ? "SUCCESS" : "FAILURE")
                .build();
    }

    @Override
    public List<PostDto> findAllByBoardId(Long id) {
        List<Post> post = repository.findAllByBoardId(id);
        if (post != null) {
            return post.stream().map(e -> entityToDto(e)).toList();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<PostDto> getPostsByBoardId(Long id) {
        return repository.getPostsByBoardId(id)
                .stream()
                .map(i -> entityToDto(i))
                .toList();
    }
}
