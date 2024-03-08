package dev.zvaryyka.nikestorebackend.util.sneakers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SneakersErrorResponse {
    private String message;
    private long timestamp;

}
