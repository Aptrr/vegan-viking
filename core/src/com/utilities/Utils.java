package com.utilities;

import java.util.Random;

public final class Utils
{    
    public static int getRandomNumber(int lower, int upper)
	{
		Random rand = new Random();

		return rand.nextInt(upper) + lower;
	}
}
