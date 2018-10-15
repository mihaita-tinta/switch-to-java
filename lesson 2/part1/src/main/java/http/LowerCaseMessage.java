package http;

public class LowerCaseMessage extends Message {
    private String message;

    public LowerCaseMessage(String message){

        this.message=message;
    }

    @Override
    public  String convertMessage(){

        return this.message.substring(10).toLowerCase();
    }
}
