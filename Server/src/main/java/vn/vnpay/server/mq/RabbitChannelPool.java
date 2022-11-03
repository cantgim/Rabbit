package vn.vnpay.server.mq;

import com.rabbitmq.client.AMQP;
import org.apache.commons.pool2.ObjectPool;

public class RabbitChannelPool {
    private ObjectPool<AMQP.Channel> pool;

    
}
