package http;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

public class UTCMessage extends Message {
    private String message;

    public UTCMessage(String message){
        this.message=message;
    }

    @Override
    public  String convertMessage(){
        Set<String> zones = ZoneId.getAvailableZoneIds();
        String zone = this.message.split(":")[1];
        System.out.println("zona: "+zone);
        if (zones.contains(zone)){
            System.out.println("Am intrat if iar returnul e : "+LocalDateTime.now(ZoneId.of(zone)).toString());
            return LocalDateTime.now(ZoneId.of(zone)).toString();
        }
        System.out.println("Nu am intrat if iar returnul e : "+LocalDateTime.now().toString());
        return LocalDateTime.now().toString();
    }
}
