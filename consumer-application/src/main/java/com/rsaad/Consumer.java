package com.rsaad;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumer {
	private static String queue = "MyFirstQueue";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername("rabbitmq");
		factory.setPassword("rabbitmq");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(queue, false, false, false, null);
		System.out.println("Waiting for messages. To exit press CTRL+C");
		
		DeliverCallback deliverCallback = (consumerTag,delivery)->{
			String message = new String(delivery.getBody(),StandardCharsets.UTF_8);
			System.out.println("Recieved '"+ message + "'");
		};
		channel.basicConsume(queue,  true, deliverCallback, consumerTag ->{});
	}
}
