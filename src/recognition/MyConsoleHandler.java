package recognition;


import java.util.logging.*;

public class MyConsoleHandler extends StreamHandler {

	//private Formatter formatter = new SimpleFormatter();

	static{System.setProperty("java.util.logging.SimpleFormatter.format",
            "%4$7s: %1$td.%<tm.%<ty %1$tT %5$s (%2$s)");
	}
	
	@Override
	public void publish(LogRecord record){      
        if(record.getLevel().intValue() < Level.WARNING.intValue()) {
            if(record.getLevel().intValue()==Level.CONFIG.intValue()) {
            	 System.out.println(record.getMessage());
            }else {

            	this.setOutputStream(System.out);
            	super.publish(record);
            }
        }else {
        	this.setOutputStream(System.err);
    		super.publish(record);
        }

    }
	
}

