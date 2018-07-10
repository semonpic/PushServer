package com.infopush.ClientServer.Msg;

public class BaseMsg {
	
	public long getSrcId() {
		return srcId;
	}
	public void setSrcId(long srcId) {
		this.srcId = srcId;
	}
	public long getDestId() {
		return destId;
	}
	public void setDestId(long destId) {
		this.destId = destId;
	}
	public int getCmd() {
		return cmd;
	}
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	long srcId=0;
	long destId=0;
	int cmd=0;
	String payload;
}
