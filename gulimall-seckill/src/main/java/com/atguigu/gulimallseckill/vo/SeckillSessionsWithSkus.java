package com.atguigu.gulimallseckill.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SeckillSessionsWithSkus {
    private Long id;
    /**
     * 场次名称
     */
    private String name;
    /**
     * 每日开始时间
     */
    private Date startTime;
    /**
     * 每日结束时间
     */
    private Date endTime;

    private Integer status;

    private Date createTime;

    private List<SeckillSkuVo> relationSkus;

}
