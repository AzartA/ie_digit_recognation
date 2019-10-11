package recognition;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.logging.ErrorManager;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
;

public class MyConsoleHandler extends Handler {
	private static String color = Color.BLACK.toString();
	protected String formata;
	private Formatter formatter = new SimpleFormatter() {
		@Override
		public String format(LogRecord record) {
			String source;
			if(record.getLevel().intValue() >= Level.WARNING.intValue()) {
	            color = Color.RED.toString();
	        }else if(record.getLevel().intValue() < Level.INFO.intValue()) {
	        	color = Color.GREEN.toString();
	        }else {
	        	color = Color.BLUE_BOLD.toString();
	        }
			
			formata = color + "%1$7s:" + Color.BLUE.toString() + " %2$td.%<tm.%<ty %2$tT " + 
		            Color.RESET.toString() + "%3$s " + Color.BLUE.toString() + "(%4$s)" + Color.RESET.toString();
			if (record.getSourceClassName() != null) {
	            source = record.getSourceClassName();
	            if (record.getSourceMethodName() != null) {
	               source += " " + record.getSourceMethodName();
	            }
	        } else {
	            source = record.getLoggerName();
	        }
	        String message = formatMessage(record);
	        return String.format(formata,
	        					record.getLevel().getLocalizedName(),    		
	        					new Date(record.getMillis()),
			                    message,
	                            source );
		}
	};
	
	  
	@Override
	public void publish(LogRecord record){   
		if (!super.isLoggable(record)) {
            return;
        }
		String msg;
		msg = record.getMessage();
		if(record.getLevel().intValue()==200) {//Level.CONFIG.intValue()) {
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

