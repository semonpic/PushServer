package com.infopush.ClientServer.Handler;

import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infopush.PushServer.Constant;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javassist.expr.NewArray;

public class JsonToStringEncoder extends MessageToByteEncoder<Object> {

	
	ObjectMapper mapper=new ObjectMapper();
	
	
    // TODO Use CharsetEncoder instead.
    private final Charset charset;

    /**
     * Creates a new instance with the current system character set.
     */
    public JsonToStringEncoder() {
        this(Charset.defaultCharset());
    }

    /**
     * Creates a new instance with the specified character set.
     */
    public JsonToStringEncoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		String tmpjsonString=mapper.writeValueAsString(msg);
		if(tmpjsonString!=null){
		tmpjsonString+=Constant.MsgDelimit;
	    byte[] barrytmp=tmpjsonString.getBytes(this.charset);
	    	out.writeBytes(barrytmp, 0, barrytmp.length);
		}
	    
	}

}
