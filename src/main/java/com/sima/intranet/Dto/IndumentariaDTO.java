package com.sima.intranet.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndumentariaDTO {
    String gerencia = "";
    List<Object[]> datos = new ArrayList<>();
}
