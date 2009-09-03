package net.asfun.template;

import java.util.Locale;
import java.util.TimeZone;

import net.asfun.template.compile.Filter;
import net.asfun.template.compile.FilterLibrary;
import net.asfun.template.compile.Importable;
import net.asfun.template.compile.Instruction;
import net.asfun.template.compile.InstructionLibrary;
import net.asfun.template.compile.Tag;
import net.asfun.template.compile.TagLibrary;
import static net.asfun.template.util.logger.JangodLogger;

public class Configuration {
	
	public static final String CONFIG_VAR = "'CFG\"CURRENT";
	
	private String encoding = "utf-8";
	private Locale locale = Locale.CHINESE;
	private TimeZone timezone = TimeZone.getDefault();
	private static Configuration defaultConfig;
	
	private String root;
	
	static {
		defaultConfig = new Configuration();
	}

	public static void addImport(Importable importee) {
		if ( importee instanceof Filter) {
			FilterLibrary.addFilter((Filter)importee);
		}
		else if ( importee instanceof Tag) {
			TagLibrary.addTag((Tag)importee);
		}
		else if ( importee instanceof Instruction ) {
			InstructionLibrary.addInstruction((Instruction)importee);
		} else {
			if ( importee != null )
				JangodLogger.warning("Can't recognize the importing object >>> " + importee.getClass().getName());
			else 
				JangodLogger.warning("Can't import null object");
		}
	}
	
	public void setEncoding(String encoding2) {
		encoding = encoding2;
	}
	
	public void setLocale(Locale locale2) {
		locale = locale2;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public TimeZone getTimezone() {
		return timezone;
	}

	public void setTimezone(TimeZone timezone2) {
		timezone = timezone2;
	}

	public static Configuration getDefaultConfig() {
		return defaultConfig;
	}

	public String getTemplateRoot() {
		return root;
	}
	
	public void setTemplateRoot(String rootPath) {
		root = rootPath;
	}
}
