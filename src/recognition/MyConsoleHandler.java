package recognition;

import java.util.logging.*;

public class MyConsoleHandler extends StreamHandler {
	private java.util.logging.Formatter formatter = new SimpleFormatter();
	static{System.setProperty("java.util.logging.SimpleFormatter.format",
            "%4$7s: %1$td.%<tm.%<ty %1$tT %5$s (%2$s)");
	}
	public void publish(LogRecord record){      
        if(record.getLevel().intValue() < Level.WARNING.intValue())
            System.out.println(formatter.format(record));            
        else
            System.err.println(formatter.format(record));
    }
}
