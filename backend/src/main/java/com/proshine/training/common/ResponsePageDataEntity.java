package com.proshine.training.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePageDataEntity<T> {
    private Long total;
    private T rows;
}
