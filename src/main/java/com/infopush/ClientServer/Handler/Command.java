package com.infopush.ClientServer.Handler;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectRestriction.ReadOnly;

public class Command {
	public static int CMD_AUTH=1;//登陆授权;
	public static int CMD_AUTHBACK=2;//登陆授权;
	public static int CMD_PING=3;//心态;
	public static int CMD_PONG=4;//心跳返回;
	public static int CMD_PROXY_ERROR=5;//转发错误,目标设备掉线
}
