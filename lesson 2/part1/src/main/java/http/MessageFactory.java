package http;

public class MessageFactory {

    public static Message getMessage(String message){
        if(message.startsWith("lowercase:")){
            return new LowerCaseMessage(message);
        }else if(message.startsWith("uppercase:")){
            return new UpperCaseMessage(message);
        }else if (message.startsWith("now:")) {
            return new UTCMessage(message);
        }else if (message.startsWith("path:")) {
            return new FileMessage(message);
        }
        return null;
    }
}
