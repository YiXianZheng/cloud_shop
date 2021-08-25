package com.shop.common.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommonPageDTO extends CommonDTO {

    @ApiModelProperty(value = "页数", required = true, example = "1")
    Integer page;

    @ApiModelProperty(value = "条数", required = true, example = "10")
    Integer rows;
}
