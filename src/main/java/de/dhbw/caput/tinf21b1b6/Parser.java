package de.dhbw.caput.tinf21b1b6;
import de.dhbw.caput.tinf21b1b6.RegularExpression.*;
final class Parser {

	public static RegularExpression parse( String string ){
		if(string.isEmpty()) return new EmptySet();
		if(string.length() == 1) return new Literal(string.charAt(0));

		int isOpenGroup = 0;
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(c == '(') isOpenGroup++;
			else if(c == ')') isOpenGroup--;
			else if(isOpenGroup != 0) continue;
			else if(c == '·')
				return new Concatenation(
						parse( string.substring(0,i-1) ),
						parse( string.substring(i+1, string.length()-1) )
				);
			else if(c == '|')
				return new Union(
						parse( string.substring(0,i-1) ),
						parse( string.substring(i+1, string.length()-1) )
				);
		}
		if(string.charAt(string.length() - 1) == '*')
			return new KleeneStar(
					parse(string.substring(0,string.length()-2))
			);
		return parse(string.substring(1,string.length()-2));
	}

}
