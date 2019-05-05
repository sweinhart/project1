package com.revature.domain;

public class FormValidation {

	public static boolean validateLettersOnly(String text, boolean nullAllowed) {
		boolean valid = true;
		if (!nullAllowed && text.contentEquals(""))
			valid = false;
		for (char c : text.toCharArray())
			if (c < 65 && c != 32 || c > 90 && c < 97 || c > 122)
				valid = false;
		return valid;
	}

	public static boolean validateNumbersOnly(String text, boolean nullAllowed) {
		boolean valid = true;
		if (!nullAllowed && text.contentEquals(""))
			valid = false;
		for (char c : text.toCharArray())
			if (c < 48 || c > 57)
				valid = false;
		return valid;
	}

	public static boolean validateMathValue(String text, boolean nullAllowed) {
		boolean valid = true;
		if (!nullAllowed && text.contentEquals(""))
			valid = false;
		int numDecimal = 0;
		for (char c : text.toCharArray()) {
			if (c == 46)
				numDecimal++;
			if (c < 48 && c != 46 || c > 57) {
				valid = false;
			}
		}			
		if (numDecimal > 1)
			valid = false;
		return valid;
	}

	public static boolean validateLettersAndNumbersOnly(String text, boolean nullAllowed) {
		boolean valid = true;
		if (!nullAllowed && text.contentEquals(""))
			valid = false;
		for (char c : text.toCharArray())
			if (c < 48 && c != 32 || c > 57 && c < 65 || c > 90 && c < 97 || c > 122)
				valid = false;
		return valid;
	}

	public static boolean validatePassword(String password) {
		boolean check1 = true;
		boolean check2 = false;
		boolean check3 = false;
		if (password.length() < 8)
			check1 = false;
		String temp = password.toLowerCase();
		if (temp.equals(password))
			check1 = false;
		temp = password.toUpperCase();
		if (temp.equals(password))
			check1 = false;
		for (char c : password.toCharArray()) {
			if (c >= 48 && c <= 57)
				check2 = true;
		}
		for (char c : password.toCharArray()) {
			if (c >= 33 && c <= 47 || c >= 58 && c <= 64 || c >= 91 && c <= 96 || c >= 123 && c <= 126)
				check3 = true;
		}
		if (!check1 || !check2 || !check3) {
			System.out.println("Password must be at least 8 characters in length,");
			System.out.println("Contain at least 1 uppercase letter,");
			System.out.println("Contain at least 1 lowercase letter,");
			System.out.println("Contain at least 1 number, and");
			System.out.println("Contain at least 1 special character\n");
			return false;
		}
		return true;
	}

	public static boolean validateEmail(String email) {
		String[] emailArray = email.split("@");
		if (emailArray.length != 2)
			return false;
		if (!validateLettersAndNumbersOnly(emailArray[0], false))
			return false;
		String[] emailArray2 = emailArray[1].split("\\.");
		if (!validateLettersAndNumbersOnly(emailArray2[0], false))
			return false;
		if (emailArray2.length != 2)
			return false;
		if ((email.endsWith(".com") || email.endsWith(".edu") || email.endsWith(".net") || email.endsWith(".org")
				|| email.endsWith(".mil") || email.endsWith(".gov")))
			return true;
		else {
			System.out.println("Invalid Email Address.");
			return false;
		}
	}
}
