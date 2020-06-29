package com.gcp.pubsubsubscriber;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
public class PubsubSubscriberApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubsubSubscriberApplication.class, args);
	}

	 @Bean
	   public PubSubInboundChannelAdapter messageChannelAdapter(
	         @Qualifier("myInputChannel") MessageChannel inputChannel,
	         PubSubTemplate pubSubTemplate) {
	      PubSubInboundChannelAdapter adapter =
	            new PubSubInboundChannelAdapter(pubSubTemplate, "subscription-28thJune2020");
	      adapter.setOutputChannel(inputChannel);
	      return adapter;
	   }
	   @Bean
	   public MessageChannel myInputChannel() {
	      return new DirectChannel();
	   }
	   @ServiceActivator(inputChannel = "myInputChannel")
	   public void messageReceiver(String payload) {
	     System.out.println("Message arrived! Payload: " + payload);
	   }
}
