package recognition;


import java.util.logging.*;

public class MyConsoleHandler extends Handler {

	private Formatter formatter = new SimpleFormatter();

	static{System.setProperty("java.util.logging.SimpleFormatter.format",
            "%4$7s: %1$td.%<tm.%<ty %1$tT %5$s (%2$s)");
	}
	  
	@Override
	public void publish(LogRecord record){   
		if (!super.isLoggable(record)) {
            return;
        }
		String msg;
		msg = record.getMessage();
		if(record.getLevel().intValue()==Level.CONFIG.intValue()) {
       	 System.out.println(msg);
       	 return;
		}
		record.setMessage(record.getMessage().replaceAll("\\n", "\\\n\\\t\\\t\\\t   ") );
		try {
            msg = formatter.format(record);
        } catch (Exception ex) {
            // We don't want to throw an exception here, but we
            // report the exception to any registered ErrorManager.
            reportError(null, ex, ErrorManager.FORMAT_FAILURE);
            return;
        }
		
		 
        if(record.getLevel().intValue() < Level.WARNING.intValue()) {
            System.out.println(msg);
        }else {
        	System.err.println(msg);
        }

    }


	@Override
	public void flush() {
		
	}


	@Override
	public void close() throws SecurityException {
	
	}
	
}

