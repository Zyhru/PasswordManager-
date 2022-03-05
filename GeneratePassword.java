import java.util.Random;

// The idea of this class is to generate a password
public class GeneratePassword {
    private Random r = new Random();




    public boolean isLengthAppropiate(int length) {
      
        return length >= 8 && length <= 12; 

    }

    public String generatePassword(int length) {

        StringBuilder sb = new StringBuilder();

        String alpha_uc = "ABCDEFGHIJKLMOPQRSTUVWXYZ";
        String alpah_lc = alpha_uc.toLowerCase();
        String numbers  = "0123456789";
        String specialCharacters = "`!@#$%^&*()-_+={}[]|;:<>,./?";


        // Select a random character from above variables in each iteration
        for(int i = 0; i < length; i++) {
            
            // Generates a number from 1-4
            int random = r.nextInt(4) + 1;
            switch(random) {
                case 1: 
                    // select a random alphabet uppercase character
                    // add it to the stringbuilder
                    sb.append(selectRandomCharacter(alpha_uc));
                    break;
                case 2: 
                    sb.append(selectRandomCharacter(alpah_lc));
                    break;
                case 3: 
                    sb.append(selectRandomCharacter(numbers));
                    break;
                case 4: 
                    sb.append(selectRandomCharacter(specialCharacters));
                    break;
            }

        }

        return sb.toString();
    }



    // Returns a random character from a string
    private char selectRandomCharacter(String input) {

        char randomChar = input.charAt(r.nextInt(input.length()));

        return randomChar;


    }



}
