package com.shop.order.vo;

import com.shop.common.vo.CommonVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("PageTestVo的Vo")
public class PageBiDdxxTJVO extends CommonVO {

    @ApiModelProperty(value = "行政区划代码")
    public String aab301;

    @ApiModelProperty(value = "订单日期")
    public String aae036;

    @ApiModelProperty(value = "平台企业单位名称")
    public String aab004;

    @ApiModelProperty(value = "关联事故备案量")
    public Integer glsgba;

    @ApiModelProperty(value = "新增订单量")
    public Integer xzddl0;

    @ApiModelProperty(value = "地区事故发生率")
    public String sgfsl0;
}
