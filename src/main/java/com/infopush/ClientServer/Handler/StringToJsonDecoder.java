package com.infopush.ClientServer.Handler;

import java.nio.charset.Charset;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infopush.ClientServer.Msg.BaseMsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class StringToJsonDecoder extends MessageToMessageDecoder<ByteBuf> {
	ObjectMapper mapper=new ObjectMapper();
    // TODO Use CharsetDecoder instead.
    private final Charset charset;

    /**
     * Creates a new instance with the current system character set.
     */
    public StringToJsonDecoder() {
        this(Charset.defaultCharset());
    }

    /**
     * Creates a new instance with the specified character set.
     */
    public StringToJsonDecoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		String jsonString=msg.toString(charset);
		BaseMsg bmsg=mapper.readValue(jsonString, BaseMsg.class);
		out.add(bmsg);
	}

}
