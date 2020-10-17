package com.example.zhifu.listener;



import com.example.zhifu.config.QueueEnum;
import com.example.zhifu.vo.PayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消订单消息的发出者
 * Created by macro on 2018/9/14.
 */
@Component
public class CancelSender {
    private static Logger LOGGER =LoggerFactory.getLogger(CancelSender.class);
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(String out_trade_no, final long delayTimes){
        //给延迟队列发送消息
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_ORDER_PLUGIN_CANCEL.getExchange(), QueueEnum.QUEUE_ORDER_PLUGIN_CANCEL.getRouteKey(),out_trade_no, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                message.getMessageProperties().setHeader("x-delay",delayTimes);
                return message;
            }
        });
        LOGGER.info("sendMessage--延时队列创建消息成功");
    }
}
