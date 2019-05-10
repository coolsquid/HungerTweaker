package coolsquid.hungertweaker.util;

import crafttweaker.api.data.DataString;
import crafttweaker.api.data.IData;

/**
 * Borrowed from https://stackoverflow.com/a/26227947
 * 
 * Modified to support an unknown variable.
 */
public interface Expression {

	double eval(double x);

	public static Expression of(final IData data) {
		if (Util.isNumber(data)) {
			double d = data.asDouble();
			return (x) -> d;
		} else if (data instanceof DataString) {
			return of(data.asString());
		} else {
			throw new IllegalArgumentException("The argument must be a string or a number");
		}
	}

	public static Expression of(final String str) {
	    return new Object() {
	        int pos = -1, ch;

	        void nextChar() {
	            ch = (++pos < str.length()) ? str.charAt(pos) : -1;
	        }

	        boolean eat(int charToEat) {
	            while (ch == ' ') nextChar();
	            if (ch == charToEat) {
	                nextChar();
	                return true;
	            }
	            return false;
	        }

	        Expression parse() {
	            nextChar();
	            Expression e = parseExpression();
	            if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
	            return e;
	        }

	        // Grammar:
	        // expression = term | expression `+` term | expression `-` term
	        // term = factor | term `*` factor | term `/` factor
	        // factor = `+` factor | `-` factor | `(` expression `)`
	        //        | number | functionName factor | factor `^` factor

	        Expression parseExpression() {
	            Expression e = parseTerm();
	            for (;;) {
	                if      (eat('+')) {
	                	Expression a = e, b = parseTerm();
	                	e = (x) -> a.eval(x) + b.eval(x);
	                } // addition
	                else if (eat('-')) {
	                	Expression a = e, b = parseTerm();
	                	e = (x) -> a.eval(x) - b.eval(x);
	                } // subtraction
	                else return e;
	            }
	        }

	        Expression parseTerm() {
	            Expression e = parseFactor();
	            for (;;) {
	                if      (eat('*')) {
	                	Expression a = e, b = parseFactor();
	                	e = (x) -> a.eval(x) * b.eval(x);
	                } // multiplication
	                else if (eat('/')) {
	                	Expression a = e, b = parseFactor();
	                	e = (x) -> a.eval(x) / b.eval(x);
	                } // division
	                else return e;
	            }
	        }

	        Expression parseFactor() {
	            if (eat('+')) return parseFactor(); // unary plus
	            if (eat('-')) return (x) -> -parseFactor().eval(x); // unary minus

	            Expression e;
	            int startPos = this.pos;
	            if (eat('(')) { // parentheses
	                e = parseExpression();
	                eat(')');
	            } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
	                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
	                double d = Double.parseDouble(str.substring(startPos, this.pos));
	                e = (x) -> d;
	            } else if (ch >= 'a' && ch <= 'z') { // functions
	            	if (ch == 'x') {
	            		e = (x) -> x;
	            		nextChar();
	            	} else {
	            		while (ch >= 'a' && ch <= 'z') nextChar();
		                String func = str.substring(startPos, this.pos);
		                e = parseFactor();
		                Expression a = e;
		                if (func.equals("sqrt")) e = (x) -> Math.sqrt(a.eval(x));
		                else if (func.equals("sin")) e = (x) -> Math.sin(Math.toRadians(a.eval(x)));
		                else if (func.equals("cos")) e = (x) -> Math.cos(Math.toRadians(a.eval(x)));
		                else if (func.equals("tan")) e = (x) -> Math.tan(Math.toRadians(a.eval(x)));
		                else if (func.equals("random")) e = (x) -> Math.random() * a.eval(x);
		                else if (func.equals("round")) e = (x) -> Math.round(a.eval(x));
		                else if (func.equals("ceil")) e = (x) -> Math.ceil(a.eval(x));
		                else throw new RuntimeException("Unknown function: " + func);
	            	}
	            } else {
	                throw new RuntimeException("Unexpected: " + (char)ch);
	            }

	            if (eat('^')) {
	            	Expression a = e, b = parseFactor();
	            	e = (x) -> Math.pow(a.eval(x), b.eval(x)); // exponentiation
	            }

	            return e;
	        }
	    }.parse();
	}
}