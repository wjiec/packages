package com.wjiec.springaio.webflux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Long id;

    private String title;

    private Double price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
