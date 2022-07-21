package com.atguigu.common.to.mq;

import lombok.Data;

import java.util.List;

@Data
public class StockLockedTo {
    /**
     * 库存工作单的id
     */
    private Long id;
    /**
     * 工作详情的所有id
     */
    private StockDetailTo detail;

}
