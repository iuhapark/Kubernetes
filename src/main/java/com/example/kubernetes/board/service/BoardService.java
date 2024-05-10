package com.example.kubernetes.board.service;

import com.example.kubernetes.board.model.Board;
import com.example.kubernetes.board.model.BoardDto;
import com.example.kubernetes.common.service.CommandService;
import com.example.kubernetes.common.service.QueryService;

import java.util.Optional;

public interface BoardService extends CommandService<BoardDto>, QueryService<BoardDto> {

    default Board dtoToEntity(BoardDto dto) {
        return Board.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }

    default BoardDto entityToDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .description(board.getDescription())
                .build();
    }


}
