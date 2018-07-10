package com.infopush.ClientServer.Handler;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.infopush.ClientServer.ChannelRepository;
import com.infopush.ClientServer.Msg.BaseMsg;
import com.infopush.ClientServer.ProtoBuf.Command.CommandType;
import com.infopush.ClientServer.ProtoBuf.Message.MessageBase;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

/**
 * 连接认证Handler
 * 1. 连接成功后客户端发送CommandType.AUTH指令，Sever端验证通过后返回CommandType.AUTH_BACK指令
 * 2. 处理心跳指令
 * 3. 触发下一个Handler
 * @author Ke Shanqiang
 *
 */
@Component
@Qualifier("authServerHandler")
@ChannelHandler.Sharable
public class AuthServerHandler extends ChannelInboundHandlerAdapter {
	public Logger log = Logger.getLogger(this.getClass());
	private final AttributeKey<Integer> clientInfo = AttributeKey.valueOf("clientInfo");
	
	@Autowired
	@Qualifier("channelRepository")
	ChannelRepository channelRepository;
	
	@SuppressWarnings("deprecation")
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//MessageBase msgBase = (MessageBase)msg;
		//String clientId = msgBase.getClientId();
		BaseMsg baseMsg=(BaseMsg)msg;
		Integer clientId=baseMsg.getSrcId();
		
		Channel ch = channelRepository.get(clientId);
		if(null == ch){
			ch = ctx.channel();
			channelRepository.put(clientId, ch);
		}
		
		/*认证处理*/
		if(baseMsg.getCmd()==Command.CMD_AUTH){
			log.info("我是验证处理逻辑");
			Attribute<Integer> attr = ctx.attr(clientInfo);
			attr.set(clientId);
			channelRepository.put(clientId, ctx.channel());
			
			ctx.writeAndFlush(createData(baseMsg.getSrcId(),baseMsg.getDestId(), Command.CMD_AUTHBACK, "This is response data"));
		
		}else if(baseMsg.getCmd()==Command.CMD_PING){
			//处理ping消息
			ctx.writeAndFlush(createData(baseMsg.getSrcId(),baseMsg.getDestId(), Command.CMD_PONG, "This is pong data"));
		
		}else if (baseMsg.getDestId()!=0) { //如果desid 为零就是转发消息，执行消息转发
			Channel destch = channelRepository.get(baseMsg.getDestId());
			if((destch!=null)&&(destch.isActive())){
				destch.writeAndFlush(baseMsg);
			}else {
				ctx.writeAndFlush(createData(baseMsg.getSrcId(),baseMsg.getDestId(), Command.CMD_PROXY_ERROR, null));	
			}
			
			
		}
		else{
			if(ch.isOpen()){
				//触发下一个handler
				ctx.fireChannelRead(msg);
				log.info("我进业务入处理逻辑");
			}
		}
		ReferenceCountUtil.release(msg);
	}
	private BaseMsg createData(int _descid,int _srcid, int cmd,String data){
		BaseMsg msg=new BaseMsg();
		msg.setDestId(_descid);
		msg.setSrcId(_srcid);
		msg.setCmd(cmd);
		msg.setPayload(data);
		return msg;
	}
}
