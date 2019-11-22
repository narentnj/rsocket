package dev.naren.example.rsocket.client;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;

@Configuration
public class ClientConfiguration {

	@Bean
	public RSocket rSocket() {
		return RSocketFactory.connect().mimeType(MimeTypeUtils.APPLICATION_JSON_VALUE, MimeTypeUtils.APPLICATION_JSON_VALUE)
				.frameDecoder(PayloadDecoder.ZERO_COPY).transport(TcpClientTransport.create(7070)).start().block();
	}

	@Bean
	RSocketRequester rSocketRequester(RSocketStrategies rSocketStrategies) {
		//return RSocketRequester.wrap(rSocket(), MimeTypeUtils.APPLICATION_JSON, null,rSocketStrategies);
		
		//return RSocketRequester.wrap(rSocket(), MimeTypeUtils.APPLICATION_JSON, MimeTypeUtils.APPLICATION_JSON, rSocketStrategies);
		
		
		InetSocketAddress address=null;
		try {
			address = new InetSocketAddress(InetAddress.getByName("localhost"), 7070);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   // log.info("RSocket server address={}", address);
	    return RSocketRequester.builder()
	        .rsocketFactory(factory -> factory
	            .dataMimeType(MimeTypeUtils.ALL_VALUE)
	            .frameDecoder(PayloadDecoder.ZERO_COPY))
	        .rsocketStrategies(rSocketStrategies)
	        .connect(TcpClientTransport.create(address))
	        .retry().block();
		
	}
}