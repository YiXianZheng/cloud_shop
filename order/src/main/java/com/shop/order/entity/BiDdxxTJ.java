package com.shop.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zyx
 * @since 2021-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BiDdxxTJ implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID0000", type = IdType.AUTO)
    private Integer id0000;

    /**
     * 行政区划代码
     */
    @TableField("AAB301")
    private String aab301;

    /**
     * 订单日期
     */
    @TableField("AAE036")
    private String aae036;

    /**
     * 平台企业服务单位编号
     */
    @TableField("AAB001")
    private String aab001;

    /**
     * 平台企业单位名称
     */
    @TableField("AAB004")
    private String aab004;

    /**
     * 新增订单量
     */
    @TableField("XZDDL0")
    private Integer xzddl0;

    /**
     * 关联事故备案量
     */
    @TableField("GLSGBA")
    private Integer glsgba;

    /**
     * 数据更新时间
     */
    @TableField("SJGXSJ")
    private LocalDateTime sjgxsj;

    /**
     * 创建时间
     */
    @TableField("CJSJ00")
    private LocalDateTime cjsj00;


}
