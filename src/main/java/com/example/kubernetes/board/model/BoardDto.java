package com.example.kubernetes.board.model;

import com.example.kubernetes.post.model.Post;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Component
@Data
@Builder(toBuilder = true)
public class BoardDto {
    private Long id;
    private String title;
    private String description;
    private String regDate;
    private String modDate;
    private List<Post> posts;
}
