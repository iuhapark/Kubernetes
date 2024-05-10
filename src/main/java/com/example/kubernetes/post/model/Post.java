package com.example.kubernetes.post.model;


import com.example.kubernetes.board.model.Board;
import com.example.kubernetes.common.model.BaseEntity;
import com.example.kubernetes.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@ToString(exclude = {"id"})
@Log4j2
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User writer;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    @Builder(builderMethodName = "builder")
    public Post(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static Post of(String title, String content){
        Post post = new Post();
        post.title = title;
        post.content = content;
        return post;
    }
}
