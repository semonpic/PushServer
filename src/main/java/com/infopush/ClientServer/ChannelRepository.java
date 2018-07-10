package com.infopush.ClientServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;

/**
 * Channel Manager
 * @author Ke Shanqiang
 *
 */
public class ChannelRepository {
	private final static Map<Integer, Channel> channelCache = new ConcurrentHashMap<Integer, Channel>();

	public void put(Integer key, Channel value) {
		channelCache.put(key, value);
	}

	public Channel get(Integer key) {
		return channelCache.get(key);
	}

	public void remove(Integer key) { 
		channelCache.remove(key);
	}

	public int size() {
		return channelCache.size();
	}
}
