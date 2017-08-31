package mg.etech.mobile.etechapp.commun.utils.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import mg.etech.mobile.etechapp.commun.utils.validator.Rules;


/**
 * 
 * @author Matelli
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateRule {
	public int order();
    public String message()     default Rules.EMPTY_STRING;
    public int messageResId()   default 0;
}
