package net.foreverdevs.jshs;

import net.foreverdevs.utils.io.ClassSeeker;
import net.foreverdevs.utils.iteration.ArrayUtils;

import java.net.URLClassLoader;

public class JavaShellScript {
	
	private Variable[] variables;
	private Class<?>[] imports;
	private ClassSeeker seeker;
	public JavaShellScript(URLClassLoader... classloader) {
		variables = new Variable[0];
		imports = new Class<?>[0];
		seeker = new ClassSeeker(ClassLoader.getSystemClassLoader() instanceof URLClassLoader
				? ArrayUtils.addAndExpand(classloader,(URLClassLoader) ClassLoader.getSystemClassLoader())
				: classloader);
	}
	public ShellScriptResult handle(String s) {
		if(s==null || s.isEmpty())return new ShellScriptResult(ShellScriptResult.Type.COMMAND_EMPTY);
		if(s.startsWith("import")) {
			s = s.replace(" ", "");
			Class<?>[] cls = seeker.seekForClasses(s.substring(6));
			if(cls.length==0)return new ShellScriptResult(ShellScriptResult.Type.IMPORT_FAILED_NO_CLASS);
			else if(cls.length==1) {
				imports = ArrayUtils.addAndExpand(imports, cls[0]);
				return new ShellScriptResult(cls, ShellScriptResult.Type.IMPORT_SUCCESSFUL);
			}
			else if(cls.length>1) return new ShellScriptResult(cls, ShellScriptResult.Type.IMPORT_FAILED_MORE_CLASSES);
		}
		return new ShellScriptResult(ShellScriptResult.Type.UNKNOWN_SYNTAX);
	}
}
