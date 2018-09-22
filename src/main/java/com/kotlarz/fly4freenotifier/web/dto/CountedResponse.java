package com.kotlarz.fly4freenotifier.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CountedResponse<T> {
    private List<T> data;

    private Long count;
}
