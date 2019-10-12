package recognition;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;


public class LogController {
	static final Log LOGGER = Log.getLogger();
	static Handler handler = new MyConsoleHandler();
	static Handler fhandler;
	static Formatter myFormatter;
	public static Level MENU = new Level("MENU", 200) {};
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
		handler.setLevel(MENU);
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
		LOGGER.f("Logger is ON");
	}
	
	public static void off () {
		setLevel("OFF","file");
		setLevel("OFF","console");
	}
}

final class Log extends Logger{
	
	
	private static String className;
	
	private Log (String statClass, String resourceBundleName) {
		super(statClass,resourceBundleName);
	}	
	
	public static Log getLogger(){
		LogManager manager = LogManager.getLogManager();
		Log l = (Log) manager.getLogger("LOGGER");
        if (l == null) {
            l = new Log("LOGGER", null);
            manager.addLogger(l);
        }
        return l;
	}
	public static Log getLogger(String name){
		className = name;
		return LogController.LOGGER;
	}
		
	//---  log set  -----------
	
	public void l(Level level, String msg) {
		super.logp(level, className, "?", msg);
	}
	
	public void l(Level level, String methodName, String msg) {
		if(methodName.contains("{")) {
			super.logp(level, className, "?", methodName, msg);
		}else {
			super.logp(level, className, methodName, msg);
		}
	}
	
	public void l(Level level, String msg, Object param) {
		super.logp(level, className, "?", msg, param);
    }
	
	public void l(Level level, String methodName, String msg, Object param) {
		super.logp(level, className, methodName, msg, param);
    }
	
	public void l(Level level, String msg, Object [] params) {
		super.logp(level, className, "?", msg, params);
    }
	
	public void l(Level level, String methodName, String msg, Object [] params) {
		super.logp(level, className, methodName, msg, params);
    }
	
	//---  debag set  -----------
	public void d(String msg) {
        l(Level.FINE, msg);
    }
	
	public void d(String methodName, String msg) {
		if(methodName.contains("{")) {
			super.logp(Level.FINE, className, "?", methodName, msg);
		}else {
			l(Level.FINE, className, methodName, msg);
		}
    }
	
	public void d(String msg, Object param) {
		super.logp(Level.FINE, className, "?", msg, param);
    }
	
	public void d(String methodName, String msg, Object param) {
		super.logp(Level.FINE, className, methodName, msg, param);
    }
	
	public void d(String msg, Object [] params) {
		super.logp(Level.FINE, className, "?", msg, params);
    }
	
	public void d(String methodName, String msg, Object [] params) {
		super.logp(Level.FINE, className, methodName, msg, params);
    }
	
	//---  finest set  -----------
	public void f(String msg) {
        l(Level.FINEST, msg);
    }
	
	public void f(String methodName, String msg ) {
        l(Level.FINEST, className, methodName, msg);
    }
	
	//---  text only set  -----------
	public void t(String msg) {
        l(LogController.MENU, msg);
    }
	
	public void t(String methodName, String msg) {
        l(LogController.MENU, methodName, msg);
    }
	
	public void t(String msg, Object param) {
        l(LogController.MENU, msg, param);
    }
	
	public void t(String methodName,  String msg, Object param) {
        l(LogController.MENU, methodName, msg, param);
    }
	
	public void t(String msg, Object[] params) {
        l(LogController.MENU, msg, params);
    }
	
	public void t(String methodName, String msg, Object[] params) {
        l(LogController.MENU, methodName, msg, params);
    }
	
	//---  config set  -----------
	public void c(String msg) {
        l(Level.CONFIG, msg);
    }
	
	public void c(String methodName, String msg) {
        l(Level.CONFIG, methodName, msg);
    }
	
	//---  warning set  -----------
	public void w(String msg) {
        l(Level.WARNING, msg);
    }
	
	public void w(String methodName, String msg) {
        l(Level.WARNING, methodName, msg);
    }
	
	//---  info set  -----------
	public void i(String msg) {
        l(Level.INFO, msg);
    }
	
	public void i(String methodName, String msg) {
        l(Level.INFO, methodName, msg);
    }
	
	//---  severe set  -----------
	public void e(String msg) {
        l(Level.SEVERE, msg);
    }
	
	public void e(String methodName, String msg) {
        l(Level.SEVERE, methodName, msg);
    }
	
	
}
