/*
 * Written By: Gregory Owen
 * Date: 3/27/12
 * Plays a game of In and Out Machine with the user:
 *  The program generates random integer coefficients for a polynomial
 *    of a specified degree.
 *  The user then inputs values and the program returns the result of the
 *    polynomial evaluated at those values
 *  After any guess, the player may guess the "rule" for the machine
 */

import java.util.Scanner;

public class InOutMachine
{
	private double[] coeffs;
	private static Scanner qwerty = new Scanner(System.in);
	
	//maxExp is the greatest power to which terms will appear in the formula
	public InOutMachine(int maxExp)
	{
	  //initialize the array of coefficients
		coeffs = new double[maxExp + 1];
		for (int coeff = 0; coeff < coeffs.length; coeff++)
		  coeffs[coeff] = (int) (10*Math.random());
	}
	
	//rounds doubles to four decimal places
	public String cleanDub(double num)
	{
		if(Math.abs(num - (int)num) < 0.0001)
			return "" + (int)num;
		else
			return "" + num;
	}
	
	//removes all spaces from input and returns it in lower case
    public static String cleanLC(String input)
    {
      return input.replaceAll("\\s", "").toLowerCase();
    }
	
	//calculates the result returned by the machine for the given input
	public double compute(double input)
	{
		double output = 0.0;
		for(int i = 1; i <= coeffs.length; i++)
			output += coeffs[coeffs.length - i] * Math.pow(input, i - 1);
		
		return output;
	}
	
	public static void generateRetort()
    {
        int choice = (int)(Math.random() * 7);
        switch(choice)
        {
            case 0:
                System.out.println("That ain't what we call a double where I come from.");
                break;
            case 1:
                System.out.println("Last time I checked, that's not a double.");
                break;
            case 2:
                System.out.println("Nice try. Please enter a double, Sparky.");
                break;
            case 3:
                System.out.println("Now hold your horses. That is NOT a double.");
                break;
            case 4:
                System.out.println("I don't know if YOU call that a double, but I sure don't.");
                break;
            case 5:
                System.out.println("boolean isThatADouble = false;");
                break;
            case 6:
                System.out.println("That's not quite not quite a double.");
                break;
        }
        
        System.out.println("Please enter a valid double.");
    }
	
	public double[] getCoeffs()
	{
		return coeffs;
	}
	
	//returns the formula for this in and out machine as a String
	//  of the form a_nx^n + ... + a_0
	public String getRule()
	{
		String correct = "";
		for(int i = 0; i < coeffs.length - 2; i++)
			correct += cleanDub(coeffs[i]) + "x^" + (coeffs.length - i - 1) + " + ";
		
		if(coeffs.length > 1)
			correct += cleanDub(coeffs[coeffs.length - 2]) + "x + ";
		
		correct += cleanDub(coeffs[coeffs.length - 1]);
		
		return correct;
	}
	
	public void interact()
	{
		String proceed = "blarg";
		String proceed2 = "gralb";
		do	//until the user decides to stop guessing
		{
			System.out.println("What value do you put into the machine?");
			String guess = "blah";
			
			do	//until a valid double is entered
			{
			  guess = qwerty.nextLine();
			  guess = guess.trim();
			  
			  if(guess.equalsIgnoreCase("q"))
			  	return;
			  
			  if(!isDouble(guess))
			  	generateRetort();
			} while(!isDouble(guess));
			
			double test = Double.valueOf(guess);
			
			System.out.println(cleanDub(test) + " goes in, " + cleanDub(compute(test)) + " comes out.");
			
			System.out.println("\nWould you like to test another input? (y/n)");
			proceed = qwerty.nextLine();
			
		} while(saidYes(proceed));
		
		System.out.println("Would you like to guess the rule? (y/n)");
		proceed = qwerty.nextLine();
		
		if(saidYes(proceed))
		{
			printRules();
			
			do	//runs until the user decides not to guess the formula anymore
			{
				System.out.println("\nWhat is your guess?");
				String formula = qwerty.nextLine();
				
				if(isRightFormula(formula))
				{
					System.out.println("Congratulations! That is correct!\n");
					return;
				}
				else
				{
					sayWrong();
					
					System.out.println("Guess again? (y/n)");
					proceed2 = qwerty.nextLine();
					
					if(saidYes(proceed2))
						return;
				}
			} while(saidYes(proceed2));
		}
	}
	
	public static boolean isDouble(String input)
    {
        if(input.length() == 0)
            return false;
        
        boolean point = false;
        
        if(input.charAt(0) != '-' && input.charAt(0) != '.' && (input.charAt(0) < '0' || input.charAt(0) > '9'))
            return false;
        
        for(int i = 1; i < input.length(); i++)
        {
            if(input.charAt(i) == '.')
            {
                if(point)
                    return false;
                else
                    point = true;
            }
            else if(input.charAt(i) < '0' || input.charAt(i) > '9')
                return false;
        }
        
        return true;
    }
	
	public boolean isRightFormula(String guess)
	{
		return (cleanLC(guess).equals(cleanLC(getRule())));
	}
	
	 public static void posIntSnark(int prevFails)
	    {
	        switch(prevFails)
	        {
	            case 0:
	                System.out.println("Nice try. Please enter a positive integer less than or equal to 10");
	                break;
	            case 1:
	                System.out.println("Ok, I get it. You want to test this program for bugs. Good luck.");
	                System.out.println("Could you please just enter a positive integer less than or equal to 10?");
	                break;
	            case 2:
	                System.out.println("Come on! You know there's not going to be a bug here!");
	                System.out.println("Just enter a freaking positive integer less than or equal to 10!");
	                break;
	            case 3:
	                System.out.println("Ha! Still trying? Enter a positive integer less than or equal to 10.");
	                break;
	            default:
	                System.out.println("Ok, jokes over. I don't have any more retorts (in this part of the code, anyways).");
	                System.out.println("Could you please enter a positive integer less than or equal to 10?!");
	                break;
	        }
	    }
	
	public static boolean positiveIntCheck(String input)   //returns whether or not input is a positive int
	  {                         
	    int length = input.length();

  	      for(int i = 0; i < length; i++)
  	      {
  	          if(input.charAt(i) > '9' || input.charAt(i) < '0')  //if each character is a digit
  	              return false;
  	      }   
  	        
  	      if(Integer.decode(input) == 0)
  	      return false;
  	    
         return true;
	  }
	
	public void printRules()
	{
		System.out.println("Here's how you guess:");
		System.out.println("1) use x as the input variable");
		System.out.println("2) put all polynomials in order from highest degree to lowest");
		System.out.println("3) don't use unnecessary parentheses");
		System.out.println("4) remember to put in all coefficients - they are important");
		System.out.println("5) constant terms and x^1 terms may be left without an exponent");
	}
	
	//returns true if response is affirmative, no if it is negative, and asks again if it is garbage
    public static boolean saidYes(String response)
    {
        response = response.trim();
        while(!validAnswer(response))
        {
                System.out.println("I'm sorry, Dave. I can't let you do that.");
                System.out.println("Please answer yes or no.");
                response = qwerty.nextLine();
        }
        
        return (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y"));
    }
	
    public static void sayWrong()
    {
        int choice = (int)(Math.random() * 4);
        
        switch(choice)
        {
            case 0:
                System.out.println("I'm sorry, but that's not quite right.");
                break;
            case 1:
                System.out.println("I'm sorry, but that response isn't correct.");
                break;
            case 2:
                System.out.println("I'm afraid that's not the rule that this machine follows.");
                break;
            case 3:
                System.out.println("Sorry, but this machine doesn't follow that rule.");
                break;
        }
    }
	
	public static boolean validAnswer(String input)
    {
        return(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")
                || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n"));
    }
	
	public static void main(String[] args)
    {
	  Scanner kboard = new Scanner(System.in);
	  
      System.out.println("Welcome to the In and Out Machine Factory!\n");
      
      do  //runs until all have been solved (or quit command breaks out)
      {
          System.out.println("On a scale of 1 to 10, how hard would you like your machine to be? (q to quit)");
          String intake = "blah";
          
          int failedAttempts = 0;
          int maxExp = 0;
          
              do  //runs until a positive int between 1 and 10 is entered (or quit command breaks out)
              {
                  intake = kboard.nextLine();
                  intake = intake.trim();
                  
                  if(intake.equalsIgnoreCase("q"))
                      break;
                  
                  if(!positiveIntCheck(intake))
                  {
                      posIntSnark(failedAttempts);
                      failedAttempts++;
                  }
                  else
                  {
                      maxExp = Integer.valueOf(intake);
                      
                      if(maxExp > 10)
                      {
                          posIntSnark(failedAttempts);
                          failedAttempts++;
                      }
                  }
              } while(!intake.equalsIgnoreCase("q") && (!positiveIntCheck(intake) || maxExp > 10));
          
          if(intake.equalsIgnoreCase("q"))
              break;
          
          InOutMachine iom = new InOutMachine(maxExp);
          
          iom.interact();
          
          System.out.println("Would you like to play again? (y/n)");
          String proceed = kboard.nextLine();
          
          if(!saidYes(proceed) || proceed.equalsIgnoreCase("q"))
            break;
          
      } while(true);
      
      System.out.println("Thanks for playing! \n-Greg");
  }
}
