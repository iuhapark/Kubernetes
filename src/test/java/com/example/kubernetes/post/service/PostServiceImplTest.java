package com.example.kubernetes.post.service;

import com.example.kubernetes.post.model.Post;
import com.example.kubernetes.post.service.PostService;
import com.example.kubernetes.post.service.PostServiceImpl;
import com.example.kubernetes.post.model.PostDto;
import com.example.kubernetes.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {
    private PostService service;
    private static Post testPost;
    @Mock
    private PostRepository repository;

    @BeforeEach
    void setup() {
        this.service = new PostServiceImpl(repository);
    }

    @BeforeEach
    void init() {
        testPost = Post.of("테스트제목", "테스트 글");
    }

    @Test
    public void 게시글_제목_검색() throws Exception {
        repository.save(testPost);
        Post post = repository.findById(1L).get();
        assertThat(post.getTitle())
                .isEqualTo("테스트제목");
    }

    @Test
    public void 게시글_전체_검색() throws Exception {
        List<Post> posts = getList();
        BDDMockito.given(repository.findAll()).willReturn(posts);
        List<PostDto> list = service.findAll();
        assertThat(list.size())
                .isEqualTo(3);

        // verify(repository, times(1)).findById(1L);
        // verify(repository, never()).findAll();
        // verifyNoInteractions(repository);
    }

    private List<Post> getList() {
        return Arrays.asList(
                Post.builder().id(1L).title("유관순").content("유관순은 3.1운동 주역이었다.").build(),
                Post.builder().id(2L).title("김구").content("김구는 임시정부 주역이었다.").build(),
                Post.builder().id(3L).title("윤봉길").content("윤봉길은 독립운동가이다.").build()
        );
    }
}