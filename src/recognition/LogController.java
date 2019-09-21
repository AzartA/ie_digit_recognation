package recognition;

import java.util.logging.*;


public class LogController {
	protected static final Logger LOGGER = Logger.getLogger(NeuronNet.class.getName());
	static Handler handler = new MyConsoleHandler();
	
	public LogController(){
		handler.setLevel(Level.INFO);
		handler.setFormatter(new SimpleFormatter());
		LOGGER.addHandler(handler);
		LOGGER.setLevel(Level.INFO);
		LOGGER.setUseParentHandlers(false);
	}
	
	public void setLevel(String logLevel) {
		Level level = Level.parse(logLevel);
		handler.setLevel(level);
		LOGGER.setLevel(level);
	}
	
	public void on () {
		setLevel("ALL");
		LOGGER.finest("Logger is ON");
	}
	
	public void off () {
		setLevel("OFF");
	}
}
