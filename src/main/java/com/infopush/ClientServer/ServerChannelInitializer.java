package com.infopush.ClientServer;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.infopush.ClientServer.Handler.IdleServerHandler;
import com.infopush.ClientServer.Handler.JsonToStringEncoder;
import com.infopush.ClientServer.Handler.StringToJsonDecoder;
import com.infopush.ClientServer.ProtoBuf.Message.MessageBase;
import com.infopush.PushServer.Constant;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

@Component
@Qualifier("serverChannelInitializer")
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	private final static int READER_IDLE_TIME_SECONDS = 20;//读操作空闲20秒
	private final static int WRITER_IDLE_TIME_SECONDS = 20;//写操作空闲20秒
	private final static int ALL_IDLE_TIME_SECONDS = 40;//读写全部空闲40秒
	ByteBuf delimiter = Unpooled.copiedBuffer(Constant.MsgDelimit.getBytes());
    @Autowired
    @Qualifier("authServerHandler")
    private ChannelInboundHandlerAdapter authServerHandler;
    
    @Autowired
    @Qualifier("logicServerHandler")
    private ChannelInboundHandlerAdapter logicServerHandler;
    
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
    	ChannelPipeline p = socketChannel.pipeline();
    	
    	p.addLast("idleStateHandler", new IdleStateHandler(READER_IDLE_TIME_SECONDS
    			, WRITER_IDLE_TIME_SECONDS, ALL_IDLE_TIME_SECONDS, TimeUnit.SECONDS));
	    p.addLast("idleTimeoutHandler", new IdleServerHandler());
	    
        //p.addLast(new ProtobufVarint32FrameDecoder());
        //p.addLast(new ProtobufDecoder(MessageBase.getDefaultInstance()));

        //p.addLast(new ProtobufVarint32LengthFieldPrepender());
        // p.addLast(new ProtobufEncoder());
	    
	    p.addLast("Frame",new DelimiterBasedFrameDecoder(2048, delimiter));
	    p.addLast("decoder", new StringToJsonDecoder(Charset.forName("UTF-8")));
	   // p.addLast("encoder", new StringEncoder(Charset.forName("UTF-8"))); 
	    p.addLast("encoder", new JsonToStringEncoder(Charset.forName("UTF-8")));
        p.addLast("authServerHandler", authServerHandler);
        p.addLast("hearableServerHandler", logicServerHandler);
    }
}
