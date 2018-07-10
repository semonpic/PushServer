package com.infopush.ClientServer.Msg;

public class BaseMsg {
	
	
	public int getSrcId() {
		return srcId;
	}
	public void setSrcId(int srcId) {
		this.srcId = srcId;
	}
	public int getDestId() {
		return destId;
	}
	public void setDestId(int destId) {
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
	int srcId=0;
	int destId=0;
	int cmd=0;
	String payload;
	@Override
	public String toString() {
		return "BaseMsg [srcId=" + srcId + ", destId=" + destId + ", cmd=" + cmd + ", payload=" + payload + "]";
	}
	
}
