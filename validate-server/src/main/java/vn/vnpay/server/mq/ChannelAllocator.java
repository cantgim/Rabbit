package vn.vnpay.server.mq;

import com.rabbitmq.client.Connection;
import stormpot.Allocator;
import stormpot.Slot;

public class ChannelAllocator implements Allocator<ChannelPoolable> {
    private Connection connection;

    public ChannelAllocator(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ChannelPoolable allocate(Slot slot) throws Exception {
        return new ChannelPoolable(slot, connection.createChannel());
    }

    @Override
    public void deallocate(ChannelPoolable poolable) throws Exception {
        connection.close();
    }
}
