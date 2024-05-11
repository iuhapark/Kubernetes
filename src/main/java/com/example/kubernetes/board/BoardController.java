package com.example.kubernetes.board;

import com.example.kubernetes.board.model.BoardDto;
import com.example.kubernetes.board.service.BoardService;
import com.example.kubernetes.common.component.Messenger;
import com.example.kubernetes.common.component.pagination.PageRequestVo;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/boards")
@Log4j2
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
})
public class BoardController {
    private final BoardService service;

    @PostMapping(path = "/save")
    public ResponseEntity<Messenger> save(@RequestBody BoardDto dto) throws SQLException {
        return ResponseEntity.ok(service.save(dto));
    }

    @PostMapping("/delete")
    public ResponseEntity<Messenger> deleteById(@RequestParam Long id) throws SQLException {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PostMapping("/modify")
    public ResponseEntity<Messenger> modify(@RequestBody BoardDto dto) throws SQLException {
        return ResponseEntity.ok(service.modify(dto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<BoardDto>> findAll(PageRequestVo vo) throws SQLException {
        log.info("게시판 목록: ");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Messenger> count() throws SQLException {
        service.count();
        return ResponseEntity.ok(new Messenger());
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsById(@RequestParam Long id) throws SQLException {
        return ResponseEntity.ok(service.existsById(id));
    }
}
