package com.shop.order.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.shop.common.dto.CommonPageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("PageTestDto的DTO")
public class PageBiDdxxTJDTO extends CommonPageDTO {

    @ApiModelProperty(value = "行政区划代码")
    private String aab301;

    @ApiModelProperty(value = "订单日期")
    private String aae036;

    @ApiModelProperty(value = "平台企业单位名称")
    @TableField("AAB004")
    private String aab004;

    @ApiModelProperty(value = "新增订单量")
    private Integer xzddl0;

    @ApiModelProperty(value = "关联事故备案量")
    private Integer glsgba;
}
