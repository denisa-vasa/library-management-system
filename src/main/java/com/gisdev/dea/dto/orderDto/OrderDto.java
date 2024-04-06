package com.gisdev.dea.dto.orderDto;

import com.gisdev.dea.dataType.Status;
import com.gisdev.dea.dto.bookDto.SimpleBookDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Status statusi;

    @NotNull(message = "Lista e librave nuk duhet te jete bosh!")
    private List<SimpleBookDto> libra;
}
