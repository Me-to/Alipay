package com.example.zhifu.config;

import lombok.Getter;

/**
 * 消息队列枚举配置
 *
 */
@Getter
public enum QueueEnum {
    /**
     * 插件实现延迟队列
     */
    QUEUE_ORDER_PLUGIN_CANCEL("qian.direct.plugin", "qian.cancel.plugin", "qian.cancel.plugin");

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
