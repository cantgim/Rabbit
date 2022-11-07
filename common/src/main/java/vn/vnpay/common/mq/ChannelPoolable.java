package vn.vnpay.common.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import stormpot.Poolable;
import stormpot.Slot;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ChannelPoolable implements Poolable {
    private final Slot slot;
    private Channel channel;

    public ChannelPoolable(Slot slot, Channel channel) {
        this.slot = slot;
        this.channel = channel;
    }

    public void queueSetup(String queueName, boolean durable, boolean exclusive, boolean autoDelete) throws IOException {
        if (channel != null) {
            channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);
        }
    }

    public void queueConsume(String queueName, boolean autoAck, DeliverCallback callback) throws IOException {
        if (channel != null) {
            channel.basicConsume(queueName, autoAck, callback, consumerTag -> {
            });
        }
    }

    @Override
    public void release() {
        slot.release(this);
    }

    public void close() throws IOException, TimeoutException {
        if (channel != null) {
            channel.close();
        }
    }
}
