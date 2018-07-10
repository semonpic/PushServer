package com.infopush.ClientServer.Handler;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class UserDelimiterBasedFrameDecoder extends DelimiterBasedFrameDecoder {

	public UserDelimiterBasedFrameDecoder(int maxFrameLength, boolean stripDelimiter, boolean failFast,
			ByteBuf delimiter) {
		super(maxFrameLength, stripDelimiter, failFast, delimiter);
		// TODO Auto-generated constructor stub
	}



}
