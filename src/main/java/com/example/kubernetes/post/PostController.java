package com.example.kubernetes.post;

import com.example.kubernetes.common.component.Messenger;
import com.example.kubernetes.post.model.PostDto;
import com.example.kubernetes.post.service.PostService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
})
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/boards/posts")
@Slf4j

public class PostController {
    private final PostService service;

    @SuppressWarnings("static-access")
    @PostMapping(path = "/save")
    public ResponseEntity<Messenger> save(@RequestBody PostDto dto) throws SQLException {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<PostDto>> findAll(Long id) throws SQLException {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Messenger> deleteById(@RequestParam Long id) throws SQLException {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping(path = "/modify")
   public ResponseEntity<Messenger> modify(@RequestBody PostDto dto){
        return ResponseEntity.ok(service.modify(dto));
    }

    @GetMapping(path = "/detail")
    public ResponseEntity<Messenger> findById(@RequestParam Long id) throws SQLException {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Messenger> count() throws SQLException {
        return ResponseEntity.ok(service.count());
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsById(@RequestParam Long id) throws SQLException {
        return ResponseEntity.ok(service.existsById(id));
    }

    @GetMapping(path = "/list/{boardId}")
    public ResponseEntity<List<PostDto>> findAllByBoardId(@PathVariable("boardId") Long id){
        return ResponseEntity.ok(service.findAllByBoardId(id));

    }
}
