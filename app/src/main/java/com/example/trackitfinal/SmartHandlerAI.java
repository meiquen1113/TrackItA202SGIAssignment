package com.example.trackitfinal;

import java.util.Random;

public class SmartHandlerAI {

    String[] tipsAI = {
            "Beware of little expenses; a small leak will sink a great ship.",
            "It’s not your salary that makes you rich; it’s your spending habits.",
            "The art is not in making money, but in keeping it.",
            "Too many people spend money they earned...to buy things they don't want...to impress people that they don't like.",
            "Do not save what is left after spending, but spend what is left after saving.",
            "A budget is telling your money where to go instead of wondering where it went.",
            "The quickest way to double your money is to fold it over and put it back in your pocket.",
            "There is a gigantic difference between earning a great deal of money and being rich.",
            "Rich people stay rich by living like they’re broke. Broke people stay broke by living like they’re rich.",
            "Financial peace isn’t the acquisition of stuff. It’s learning to live on less than you make, so you can give money back and have money to invest."
    };

    // Function to get a random element from an array
    public String getRandomElement(String[] array) {
        Random random = new Random();
        int randomIndex = random.nextInt(array.length);  // Generate a random index
        return array[randomIndex];  // Return the element at the random index
    }

    // Generate a random financial tips message string
    public String getSplashMessage() {
        return getRandomElement(tipsAI);
    }

    // Generate a message when cash flow is low
    public String getCashFlowMessage(float cash_flow, float income) {
        if (cash_flow != 0) {
            if (cash_flow < 0) {
                return ("Warning! Your cash flow is negative. Start saving now!");
            } else if (cash_flow <= 0.5 * income) {
                return ("Warning: Your cash flow is low and nearing a negative balance. Please review your expenses to avoid debts!");
            } else {
                return ("Your cash flow is good. Continue to spend wisely!");
            }
        }
        return "Welcome to TrackIt! Your expense tracking journey start today!";
    }
}