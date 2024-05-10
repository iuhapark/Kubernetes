package com.example.kubernetes.post.service;


import com.example.kubernetes.common.service.CommandService;
import com.example.kubernetes.common.service.QueryService;
import com.example.kubernetes.post.model.Post;
import com.example.kubernetes.post.model.PostDto;

import java.util.List;

public interface PostService extends CommandService<PostDto>, QueryService<PostDto> { //인터페이스 확장, 다중 상속 허용

    default Post dtoToEntity(PostDto dto) {
        return Post.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }

    default PostDto entityToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    List<PostDto> findAllByBoardId(Long id);

    List<PostDto> getPostsByBoardId(Long boardId);

}
