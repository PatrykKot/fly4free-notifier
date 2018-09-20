package com.kotlarz.fly4freenotifier.web.dto.notified;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifiedUserDto {
    private Long id;

    private String email;

    private List<PhraseDto> phrases;
}
