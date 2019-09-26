package recognition;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;


public class LogController {
	private static final Logger LOGGER = Logger.getLogger(NeuronNet.class.getName());
	static Handler handler = new MyConsoleHandler();
	static Handler fhandler;
	static Formatter myFormatter;
	static { 
		myFormatter = new SimpleFormatter() {
			private static final String format = "%1$7s: %2$td.%<tm.%<ty %<tT  %3$s (%4$s - %5$s)%n";
	        @Override
	        public synchronized String format(LogRecord lr) {
	      	  lr.setMessage(this.formatMessage(lr));
	            //if(lr.getLevel()==Level.CONFIG) {
	          	  lr.setMessage(lr.getMessage().replaceAll("\\n", "\\\n\\\t\\\t\\\t\\\t\\\t\\\t\\\t"));
	            //}
	      	  return String.format(format,
	                    lr.getLevel().getLocalizedName(),
	      			  new Date(lr.getMillis()),
	                    lr.getMessage(),
	                    lr.getSourceClassName(),
	                    lr.getSourceMethodName()
	            );
	        }
		};
		try {
			fhandler = new FileHandler("ie_digit_recognation.log");
		}catch(IOException | SecurityException e) {
			e.printStackTrace();
		}
	}
	public static void configure(){
		handler.setLevel(Level.CONFIG);
		LOGGER.addHandler(handler);
		fhandler.setLevel(Level.ALL);
		fhandler.setFormatter(myFormatter);
		LOGGER.addHandler(fhandler);
		LOGGER.setLevel(Level.ALL);
		LOGGER.setUseParentHandlers(false);
	}
	
	public static void setLevel(String logLevel) {
		Level level = Level.parse(logLevel);
		handler.setLevel(level);
	}
	
	public static void setLevel(String logLevel, String out) {
		Level level = Level.parse(logLevel);
		switch (out) {
		case "file":
			fhandler.setLevel(level);
			break;
		case "console":
			handler.setLevel(level);
			break;
		}
	}
	
	public static void on () {
		setLevel("ALL","file");
		setLevel("ALL","console");
		LOGGER.finest("Logger is ON");
	}
	
	public static void off () {
		setLevel("OFF","file");
		setLevel("OFF","console");
	}
}
