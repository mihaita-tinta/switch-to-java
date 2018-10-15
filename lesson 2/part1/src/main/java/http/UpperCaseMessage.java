package http;

public class UpperCaseMessage extends Message {
    private String message;

    public UpperCaseMessage(String message){

        this.message=message;
    }

    @Override
    public  String convertMessage(){

        return this.message.substring(10).toUpperCase();
    }
}
