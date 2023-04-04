package de.dhbw.caput.tinf21b1b6;
import de.dhbw.caput.tinf21b1b6.RegularExpression.*;
final class Parser {

	public static RegularExpression parse(String string ){
		if(string.isEmpty()) return new EmptySet();
		if(string.length() == 1){
			if(string.charAt(0) == 'ε') return new EmptyWord();
			return new Literal(string.charAt(0));
		}

		int isOpenGroup = 0;
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(c == '(') isOpenGroup++;
			else if(c == ')') isOpenGroup--;
			else if(isOpenGroup != 0) continue;
			else if(c == '·')
				return new Concatenation(
						parse( string.substring(0,i) ),
						parse( string.substring(i+1, string.length()) )
				);
			else if(c == '|')
				return new Union(
						parse( string.substring(0,i) ),
						parse( string.substring(i+1, string.length()) )
				);
		}
		if(string.charAt(string.length() - 1) == '*')
			return new KleeneStar(
					parse(string.substring(0,string.length()-1))
			);
		return parse(string.substring(1,string.length()- 1));
	}

}
