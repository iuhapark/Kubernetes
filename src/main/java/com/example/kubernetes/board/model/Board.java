package com.example.kubernetes.board.model;

import com.example.kubernetes.common.model.BaseEntity;
import com.example.kubernetes.post.model.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "boards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
@ToString(exclude = {"id"})
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Post> posts;

    @Builder(builderMethodName = "builder")
    public Board(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}

