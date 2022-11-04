package vn.vnpay.server.mq;

import com.rabbitmq.client.Channel;
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

    public Channel getChannel() {
        return channel;
    }

    @Override
    public void release() {
        slot.release(this);
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
    }
}
