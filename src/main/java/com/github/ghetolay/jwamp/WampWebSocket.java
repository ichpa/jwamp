package com.github.ghetolay.jwamp;


import java.io.IOException;

import com.github.ghetolay.jwamp.event.WampEventSubscriber;
import com.github.ghetolay.jwamp.message.WampCallResultMessage;
import com.github.ghetolay.jwamp.rpc.WampRPCSender;
import com.github.ghetolay.jwamp.utils.ResultListener;

public class WampWebSocket {

	private WampConnection connection;
	
	private WampRPCSender rpcSender;
	private WampEventSubscriber eventSubscriber;
	
	public WampWebSocket(WampConnection connection) {
		this.connection = connection;
	}

	public WampConnection getConnection(){
		return connection;
	}
	
	private WampRPCSender getRPCSender() throws UnsupportedWampActionException{
		//Refresh in case it was removed from the connection
		if(rpcSender == null || connection.containsMessageHandler(rpcSender)){
			rpcSender = connection.getMessageHandler(WampRPCSender.class);
			if(rpcSender == null)
				throw new UnsupportedWampActionException();
		}
		return rpcSender;
	}
	
	private WampEventSubscriber getEventSubscriber() throws UnsupportedWampActionException{
		//Refresh in case it was removed from the connection
		if(eventSubscriber == null || connection.containsMessageHandler(eventSubscriber)){
			eventSubscriber = connection.getMessageHandler(WampEventSubscriber.class);
			if(eventSubscriber == null)
				throw new UnsupportedWampActionException();
		}
		return eventSubscriber;
	}
	
	public WampCallResultMessage call(String procId, Object args, long timeout) throws IOException, UnsupportedWampActionException{
		return getRPCSender().call(procId, args, timeout);
	}
	
	public String call(String procId, Object args, long timeout, ResultListener<WampCallResultMessage> listener) throws IOException, UnsupportedWampActionException{
		return getRPCSender().call(procId, args, timeout, listener);
	}
	
	public void subscribe(String topicId) throws IOException, UnsupportedWampActionException{
		getEventSubscriber().subscribe(topicId);
	}
	
	public void unsubscribe(String topicId) throws IOException, UnsupportedWampActionException{
		getEventSubscriber().unsubscribe(topicId);
	}
	
	public void publish(String topicId, Object event) throws IOException, UnsupportedWampActionException{
		getEventSubscriber().publish(topicId, event);
	}
	public void publish(String topicId, Object event, boolean excludeMe) throws IOException, UnsupportedWampActionException{
		getEventSubscriber().publish(topicId, event, excludeMe);
	}
	public void publish(String topicId, Object event, boolean excludeMe, String[] eligible) throws IOException, UnsupportedWampActionException{
		getEventSubscriber().publish(topicId, event, excludeMe, eligible);
	}
	public void publish(String topicId, Object event, String[] exclude, String[] eligible) throws IOException, UnsupportedWampActionException{
		getEventSubscriber().publish(topicId, event, exclude, eligible);
	}
	
}