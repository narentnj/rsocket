package dev.naren.example.rsocket.client;

import org.reactivestreams.Publisher;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
	private final RSocketRequester rSocketRequester;
	 
    public SampleController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }
 
    @GetMapping(value = "/current/{resource}")
    public Publisher<String> current(@PathVariable("resource") String resource) {
        return rSocketRequester
          .route("stream")
          .data(resource)
          .retrieveFlux(String.class);
    }
}
