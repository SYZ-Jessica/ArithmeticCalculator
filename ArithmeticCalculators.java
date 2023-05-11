import java.lang.*;
import java.io.*;
import java.util.*;
public class ArithmeticCalculators1 {
	private static String answer;
	ArrayStack<String> valStk=new ArrayStack<String>();
	ArrayStack<String> opStk=new ArrayStack<String>();
	String val="";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String question;
		// Using FileIO to read and write files
		try {
			//Create a text file
			File file= new File("results.txt");
			File inputFile=new File("questions.txt");
			Scanner sc= new Scanner(inputFile);
			FileWriter fw=new FileWriter(file);
			// Go through each line in the input file
			while(sc.hasNextLine()) {
				question=sc.nextLine();		
				// Give the instance to each input line
				answer=new ArithmeticCalculators1().EvalExp(question)[0];
				// Write the values in the file
				fw.write("\nQuestion: "+question);
				fw.write("\nResult: "+answer);
			}
			sc.close();
			fw.close();
		}catch(FileNotFoundException e) {
			System.out.println("File not found!");
		}catch(IOException e) {
			System.out.println("Error!");
		}
	}
	
	public String[] EvalExp(String input) {	
		// counting the index
		int count=0;
		for(int i=0; i<input.length();i++) {
			count=i+1;
			// check if the each character of the input line has space, if there's no space, keep the previous value "val", otherwise
			// set it to empty space. This is for the case like "14","==","!="
			if(input.charAt(i)==' ') {
				// identify the character type and saved them to valStk and opStk respectively
				if(isNumber(val)) {
					valStk.push(val);
				}else if(isOpenParenthesis(val)) {
					// take the substring start from one character after the open parenthesis
					String substring=input.substring(i+1);
					// this is where to save the return value when finish operate the substring
					String[] temp=new ArithmeticCalculators1().EvalExp(substring);
					// save the outcome of the substring result
					valStk.push(temp[0]);
					// using the "count" number to count the number of substring has be traversed, then added up to the original "i"
					// to skip the whole parenthesis part
					i+=Integer.parseInt(temp[1]);		
				}else if(isCloseParenthesis(val)) {
					// stop compute the substring when meet the close parenthesis
					break;
				}else {
					// test if the elements in the stack is computable, if not save the binary operators into the opStk
					repeatOp(val);
					opStk.push(val);
				}
				//reset it to empty space
				val="";
			}else {
				val+=input.charAt(i);
			}	
		}
		// Use $ as special “end of input” token with lowest precedence
		repeatOp("$");
		String[] returnArray= {valStk.top(),Integer.toString(count)};
		return returnArray;
	}
		
	public static boolean isNumber(String b) {
		try{
			Double.parseDouble(b);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}		
	}	
	
	public static boolean isOpenParenthesis(String b) {
		if(b.equals("("))
			return true;
		else
			return false;
	}
	
	public static boolean isCloseParenthesis(String b) {
		if(b.equals(")"))
			return true;
		else
			return false;
	}
	
	public void repeatOp(String refOp) {
		while(valStk.size()>1 && prec(refOp)<=prec(opStk.top())) {	
			doOp();
		}
	}
	
	public static int prec(String operator) {
		// assign the precedence to each operator
		switch(operator) {
			case "^":
				return 5;
			case "*":
			case "/":
				return 4;
			case "+":
			case "-":
				return 3;
			case ">":
			case ">=":
			case "<":
			case "<=":
				return 2;
			case "==":
			case "!=":
				return 1;
			default:
				return 0;
		}
	}
	
	// Compute the elements
	public void doOp() {
		double x=Double.parseDouble(valStk.pop());
		double y=Double.parseDouble(valStk.pop());
		String op=opStk.pop();
		switch(op) {
			case "^":
				valStk.push(Math.pow(y,x)+"");
				break;
			case "*":
				valStk.push(y*x+"");
				break;
			case "/":
				valStk.push(y/x+"");
				break;
			case "+":
				valStk.push(y+x+"");
				break;
			case "-":
				valStk.push(y-x+"");
				break;
			case "<=":
				if(y<=x) 
					valStk.push(true+"");
				else
					valStk.push(false+"");
				break;
			case "<":
				if(y<x) 
					valStk.push(true+"");
				else
					valStk.push(false+"");
				break;
			case ">=":
				if(y>=x) 
					valStk.push(true+"");
				else
					valStk.push(false+"");
				break;
			case ">":
				if(y>x) 
					valStk.push(true+"");
				else
					valStk.push(false+"");
				break;
			case "==":
				if(y==x)
					valStk.push(true+"");
				else
					valStk.push(false+"");
				break;
			case "!=":
				if(y!=x)
					valStk.push(true+"");
				else
					valStk.push(false+"");
				break;
			default:
				break;			
		}
	}	
}
