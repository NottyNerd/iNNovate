package io.innovate.innovate.Model;

import io.innovate.innovate.Model.ServerResponse;

/**
 * Created by NottyNerd on 06/02/2017.
 */
public class ServerEvent {
    private ServerResponse serverResponse;

    public ServerEvent(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

    public ServerResponse getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }
}
