package coolsquid.hungertweaker.util;

import java.util.Random;

import crafttweaker.api.data.DataString;
import crafttweaker.api.data.IData;

/**
 * Borrowed from https://stackoverflow.com/a/26227947
 *
 * Modified to support an unknown variable and functions with multiple
 * parameters.
 */
public interface Expression {

	double execute(double x);

	public static Expression parse(final IData data) {
		if (Util.isNumber(data)) {
			final double d = data.asDouble();
			return (x) -> d;
		} else if (data instanceof DataString) {
			return parse(data.asString());
		} else {
			throw new IllegalArgumentException("The argument must be a string or a number");
		}
	}

	public static Expression parse(final String str) {
		return new Object() {
			int pos = -1;
			char ch;

			void nextChar() {
				ch = (++pos < str.length()) ? str.charAt(pos) : (char) -1;
			}

			boolean eat(final char charToEat) {
				while (ch == ' ') {
					nextChar();
				}
				if (ch == charToEat) {
					nextChar();
					return true;
				}
				return false;
			}

			Expression parse() {
				if (str.isEmpty()) {
					throw new RuntimeException("Cannot create an expression from an empty string");
				}
				nextChar();
				final Expression e = parseExpression();
				if (pos < str.length()) {
					throw new RuntimeException("Unexpected char '" + ch + "' at index " + pos);
				}
				return e;
			}

			// Grammar:
			// expression = term | expression `+` term | expression `-` term
			// term = factor | term `*` factor | term `/` factor
			// factor = `+` factor | `-` factor | `(` expression `)`
			// | number | functionName factor | factor `^` factor

			Expression parseExpression() {
				Expression e = parseTerm();
				for (;;) {
					if (eat('+')) {
						final Expression a = e, b = parseTerm();
						e = (x) -> a.execute(x) + b.execute(x);
					} // addition
					else if (eat('-')) {
						final Expression a = e, b = parseTerm();
						e = (x) -> a.execute(x) - b.execute(x);
					} // subtraction
					else {
						return e;
					}
				}
			}

			Expression parseTerm() {
				Expression e = parseFactor();
				for (;;) {
					if (eat('*')) {
						final Expression a = e, b = parseFactor();
						e = (x) -> a.execute(x) * b.execute(x);
					} // multiplication
					else if (eat('/')) {
						final Expression a = e, b = parseFactor();
						e = (x) -> a.execute(x) / b.execute(x);
					} // division
					else {
						return e;
					}
				}
			}

			Expression nextParameter() {
				if (!eat(',')) {
					throw new RuntimeException("Expected another parameter at index " + pos);
				}
				return parseExpression();
			}

			Expression parseFactor() {
				if (eat('+')) {
					return parseFactor(); // unary plus
				}
				if (eat('-')) {
					final Expression e = parseFactor();
					return (x) -> -e.execute(x); // unary minus
				}

				Expression e;
				final int startPos = this.pos;
				if (eat('(')) { // parentheses
					e = parseExpression();
					if (!eat(')')) {
						throw new RuntimeException("Expected ')' at index " + pos);
					}
				} else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
					while ((ch >= '0' && ch <= '9') || ch == '.') {
						nextChar();
					}
					final double d = Double.parseDouble(str.substring(startPos, this.pos));
					e = (x) -> d;
				} else if (ch >= 'a' && ch <= 'z') { // functions
					while (ch >= 'a' && ch <= 'z') {
						nextChar();
					}
					final String func = str.substring(startPos, this.pos);
					if (eat('(')) {
						if (func.equals("random")) {
							if (eat(')')) {
								e = (x) -> Math.random();
							} else {
								e = parseExpression();
								final Expression a = e;
								final Random random = new Random();
								e = (x) -> random.nextInt((int) a.execute(x));
								if (!eat(')')) {
									throw new RuntimeException("Expected ')' at index " + pos);
								}
							}
						} else {
							e = parseExpression();
							final Expression a = e;
							if (func.equals("sqrt")) {
								e = (x) -> Math.sqrt(a.execute(x));
							} else if (func.equals("sin")) {
								e = (x) -> Math.sin(Math.toRadians(a.execute(x)));
							} else if (func.equals("cos")) {
								e = (x) -> Math.cos(Math.toRadians(a.execute(x)));
							} else if (func.equals("tan")) {
								e = (x) -> Math.tan(Math.toRadians(a.execute(x)));
							} else if (func.equals("round")) {
								e = (x) -> Math.round(a.execute(x));
							} else if (func.equals("ceil")) {
								e = (x) -> Math.ceil(a.execute(x));
							} else if (func.equals("nextUp")) {
								e = (x) -> Math.nextUp(a.execute(x));
							} else if (func.equals("nextDown")) {
								e = (x) -> Math.nextDown(a.execute(x));
							} else if (func.equals("max")) {
								final Expression b = nextParameter();
								e = (x) -> Math.max(a.execute(x), b.execute(x));
							} else if (func.equals("min")) {
								final Expression b = nextParameter();
								e = (x) -> Math.min(a.execute(x), b.execute(x));
							} else if (func.equals("clamp")) {
								final Expression b = nextParameter();
								final Expression c = nextParameter();
								e = (x) -> {
									final double aa = a.execute(x), bb = b.execute(x), cc = c.execute(x);
									if (aa < bb) {
										return bb;
									} else if (aa > cc) {
										return cc;
									}
									return aa;
								};
							} else {
								throw new RuntimeException("Unknown function: " + func);
							}
							if (!eat(')')) {
								throw new RuntimeException("Expected ')' at index " + pos);
							}
						}
					} else {
						if (func.equals("x")) {
							e = (x) -> x;
						} else {
							throw new RuntimeException("Expected '(' at index " + pos);
						}
					}
				} else {
					throw new RuntimeException("Unexpected char '" + ch + "' at index " + pos);
				}

				if (eat('^')) {
					final Expression a = e, b = parseFactor();
					e = (x) -> Math.pow(a.execute(x), b.execute(x)); // exponentiation
				}

				return e;
			}
		}.parse();
	}
}