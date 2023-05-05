package ru.len4ass.api.jservice;

import org.springframework.stereotype.Service;

@Service
public class JServiceClient {
    private final String mainUri = "http://jservice.io";

    private final JServiceRandomEndpoint randomEndpoint = new JServiceRandomEndpoint(mainUri);

    public JServiceRandomEndpoint getJServiceRandomEndpoint() {
        return randomEndpoint;
    }

    public JServiceClient() {

    }
}
