package dev.naren.example.rsocket.server;

import java.time.Duration;
import java.util.Random;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;

@Controller
public class SampleController {

	@MessageMapping("stream")
	public Flux<String> getStream(String input) {
		return Flux.interval(Duration.ofSeconds(2)).map(x -> this.randomMessage(input));
	}

	private String randomMessage(String name) {
		
		return name+"_"+getAlphaNumericString(6)+"\n";
	}

	private String getAlphaNumericString(int n) {
		// lower limit for LowerCase Letters
		int lowerLimit = 97;
		// lower limit for LowerCase Letters
		int upperLimit = 122;
		Random random = new Random();
		// Create a StringBuffer to store the result
		StringBuffer r = new StringBuffer(n);
		for (int i = 0; i < n; i++) {
		// take a random value between 97 and 122
			int nextRandomChar = lowerLimit + (int) (random.nextFloat() * (upperLimit - lowerLimit + 1));
			// append a character at the end of bs
			r.append((char) nextRandomChar);
		}
	// return the resultant string
		return r.toString();
	}
}
