package com.example.kubernetes.post.model;

import lombok.*;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Component
@Data
@Builder(toBuilder = true)
@Getter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long writerId;
    private Long boardId;
    private String regDate;
    private String modDate;
}
