import net.foreverdevs.utils.DateFormat;
import net.foreverdevs.utils.NumberUtils;
import net.foreverdevs.utils.exceptions.ExceptionUtils;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class Main {
    public static void main(String[] args){
        System.out.println(new DateFormat(DateFormat.HOUR,DateFormat.MINUTE,DateFormat.SECOND).format(OffsetDateTime.now(ZoneOffset.UTC)));
    }
}
