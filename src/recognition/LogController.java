package recognition;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;


public class LogController {
	static final Logger LOGGER = Logger.getLogger(NeuronNet.class.getName());
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

final class Log {
	private Logger LOGGER;
	private Level MENU;
	private String className;
	
	public static Log get(Class<?> clazz) {
		    return new Log(clazz);
	}

	private Log(Class<?> clazz) {
		this.className = clazz.getName();
		LOGGER = Logger.getLogger(clazz.getName());
		MENU = new Level("MENU", 200) {};
	}
	
	public static Log get(String statClass) {
	    return new Log(statClass);
}

	private Log(String statClass) {
		this.className = statClass;
		LOGGER = Logger.getLogger(statClass);
		MENU = new Level("MENU", 200) {};
	}
	
	//---  log set  -----------
	public void log(Level level, String msg) {
		LOGGER.logp(level, className, "?", msg);
	}
	
	public void log(Level level, String methodName, String msg) {
		if(methodName.contains("{")) {
			LOGGER.logp(level, className, "?", methodName, msg);
		}else {
			LOGGER.logp(level, className, methodName, msg);
		}
	}
	
	public void log(Level level, String msg, Object param) {
		LOGGER.logp(level, className, "?", msg, param);
    }
	
	public void log(Level level, String methodName, String msg, Object param) {
		LOGGER.logp(level, className, methodName, msg, param);
    }
	
	public void log(Level level, String msg, Object [] params) {
		LOGGER.logp(level, className, "?", msg, params);
    }
	
	public void log(Level level, String methodName, String msg, Object [] params) {
		LOGGER.logp(level, className, methodName, msg, params);
    }
	
	//---  debag set  -----------
	public void d(String msg) {
        log(Level.FINE, msg);
    }
	
	public void d(String methodName, String msg) {
		if(methodName.contains("{")) {
			LOGGER.logp(Level.FINE, className, "?", methodName, msg);
		}else {
			log(Level.FINE, className, methodName, msg);
		}
    }
	
	public void d(String msg, Object param) {
		LOGGER.logp(Level.FINE, className, "?", msg, param);
    }
	
	public void d(String methodName, String msg, Object param) {
		LOGGER.logp(Level.FINE, className, methodName, msg, param);
    }
	
	public void d(String msg, Object [] params) {
		LOGGER.logp(Level.FINE, className, "?", msg, params);
    }
	
	public void d(String methodName, String msg, Object [] params) {
		LOGGER.logp(Level.FINE, className, methodName, msg, params);
    }
	
	//---  finest set  -----------
	public void f(String msg) {
        log(Level.FINEST, msg);
    }
	
	public void f(String msg, String className, String methodName) {
        log(Level.FINEST, className, methodName, msg);
    }
	
	//---  text only set  -----------
	public void t(String msg) {
        log(MENU, msg);
    }
	
	public void t(String msg, String methodName) {
        log(MENU, className, methodName, msg);
    }
	
	
	
	
	
	
	//---  config set  -----------
	public void c(String msg) {
        log(Level.CONFIG, msg);
    }
	
	public void c(String msg, String className, String methodName) {
        log(Level.CONFIG, className, methodName, msg);
    }
	
	//---  warning set  -----------
	public void w(String msg) {
        log(Level.WARNING, msg);
    }
	
	public void w(String msg, String className, String methodName) {
        log(Level.WARNING, className, methodName, msg);
    }
	
	//---  info set  -----------
	public void i(String msg) {
        log(Level.INFO, msg);
    }
	
	public void i(String msg, String className, String methodName) {
        log(Level.INFO, className, methodName, msg);
    }
	
	//---  severe set  -----------
	public void e(String msg) {
        log(Level.SEVERE, msg);
    }
	
	public void e(String msg, String className, String methodName) {
        log(Level.SEVERE, className, methodName, msg);
    }
	
	
}
