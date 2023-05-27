package org.dietrich.handlers;

public class MessageGateway {
    public String handleMessage(String message) {
        switch (message.split(" ")[0]) {
            case GetHandler.COMMAND:
                return new GetHandler().parseMessage(message);
            default:
                return null;
        }
    }
}
