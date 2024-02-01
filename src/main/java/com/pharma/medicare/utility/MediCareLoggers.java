package com.pharma.medicare.utility;

import org.slf4j.Logger;

public class MediCareLoggers {
	
	public static void info(Logger LOGGER, String message) {
		LOGGER.debug("Info :: " + message);
	}

}
