package com.example.kubernetes.post.repository;

import com.example.kubernetes.post.model.Post;
import com.example.kubernetes.post.model.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByBoardId(Long id);

    //JPQL Default 방식
    @Query("select a from Post a where a.board.id = :boardId")
    List<Post> getPostsByBoardId(@Param("boardId") Long boardId);

    //Native 방식
    @Query(value = "select a from Post a where a.board.id = 1", nativeQuery = true)
    public List<Map<String, Object>> getQnaPosts(@Param("boardId") Long boardId);

    //JPSQL return type Dto
    String postDtoMapping = "new com.example.kubernetes.post.model.PostDto(" +
            "a.id, a.title, a.content, a.writer.id, a.board.id," +
            "a.regDate, a.modDate)";
    @Query ("select "+postDtoMapping + "from Post a where a.board.id = :boardId")
    List<PostDto> getPostDTOsByBoardId(@Param("boardId") Long boardId);

    List <Post> findAllByOrderByIdDesc();


}
