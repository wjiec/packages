package com.wjiec.springaio.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {

    @NotNull(message = "must be choose at least 1 item")
    @Size(min = 1, message = "must be choose at least {min} item")
    List<Long> itemIds;

}
