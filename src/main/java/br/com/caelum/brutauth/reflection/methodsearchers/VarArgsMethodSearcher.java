package br.com.caelum.brutauth.reflection.methodsearchers;

import java.lang.reflect.Method;

import br.com.caelum.brutauth.auth.rules.CustomBrutauthRule;
import br.com.caelum.brutauth.reflection.BrutauthMethod;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class VarArgsMethodSearcher implements MethodSearcher {

	private final DefaultMethodSearcher defaultMethodSearcher;

	public VarArgsMethodSearcher(DefaultMethodSearcher defaultMethodSearcher) {
		this.defaultMethodSearcher = defaultMethodSearcher;
	}
	
	@Override
	public BrutauthMethod search(CustomBrutauthRule ruleToSearch,
			Object... withArgs) {
		try {
			Method defaultMethod = defaultMethodSearcher.getMethod(ruleToSearch);
			return new BrutauthMethod(fakeVarArgs(withArgs), defaultMethod, ruleToSearch);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	private Object[] fakeVarArgs(Object[] args) {
		if(args == null) return fakeVarArgs(new Object[]{args});
		return new Object[]{args};
	}
}
