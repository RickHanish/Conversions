import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main{

	//USD, GBP, CAD, INR
	static double[][] CURRENCY_FACTORS =
			{{1, 0.72284696, 1.2318518, 74.504323},
			 {1.3834187, 1, 1.7041668, 103.07067},
			 {0.81178597, 0.58679702, 1, 60.481564},
			 {0.01342204, 0.0097020808, 0.0165340, 1}};

	//Meter, foot, inch, kilometer, centimeter
	static double[][] DISTANCE_FACTORS =
			{{1, 3.28084, 39.3701, 1000, 0.01},
			 {0.3048, 1, 12, 0.0003048, 30.48},
			 {0.0254, 0.08333333333, 1, .0000254, 2.54},
			 {1000, 3280.84, 39370.1, 1, 100000},
			 {0.01, 0.0328084, 0.393701, 0.00001, 1}};

	//Pound, ounce, gram, kilogram
	static double[][] WEIGHT_FACTORS =
			{{1, 16, 453.592, 0.4535592},
			 {0.0625, 1, 28.3495, 0.0283495},
			 {0.00220462, 0.035274, 1, 0.001},
			 {2.20462, 35.274, 1000, 1}};

	//Liter, milliliter, fluid ounce, cup, gallon, quart, pint, tablespoon, teaspoon
	static double[][] VOLUME_FACTORS =
			{{1, 1000, 33.814, 4.16667, 0.264172, 1.05669, 2.11338, 67.628, 202.884}, //Liter
			 {0.001, 1, 0.033814, 0.00416667, 0.000264172, 0.00105669, 0.00211338, 0.67628, 0.202884}, //Milliliter
			 {0.0295735, 29.5735, 1, 0.123223, 0.0078125, 0.03125, 0.0625, 2, 6}, //Fluid ounce
			 {0.24, 240, 8.11537, 1, 0.0634013, 0.253605, 0.50721, 16.2307, 48.6922}, //cup
			 {3.78541, 3785.41, 128, 15.7725, 1, 4, 8, 256, 768}, //Gallon
			 {0.946353, 946.353, 32, 3.94314, 0.25, 1, 2, 64, 192}, //Quart
			 {0.473176, 473.176, 16, 1.97157, 0.125, 0.5, 1, 32, 96}, //Pint
			 {0.0147868, 14.7868, 0.5, 0.0616115, 0.00390625, 0.015625, 0.03125, 1, 3}, //Tablespoon
			 {0.00492892, 4.92892, 0.166667, 0.0205372, 0.00130208, 0.00520833, 0.0104167, 0.333333, 1}}; //Teaspoon

	public static void main(String[] args){
		String[] menu = {"Currency (As of July 4, 2021)", "Distance", "Temperature", "Number Systems", "Weight", "Volume"};
		String lastChosen = menu[0];
		while(true){
			String inputValue = JOptionPane.showInputDialog(null,
					"Choose conversion category", "Menu", JOptionPane.PLAIN_MESSAGE,
					null, menu, lastChosen).toString();
			lastChosen = inputValue;
			switch(inputValue){
				case "Currency (As of July 4, 2021)":
					ArrayList<String> options = new ArrayList<>(Arrays.asList("US Dollar", "British Pound", "Canadian Dollar", "Rupee"));
					String[] values = input(options, "currency");
					String stringToPrint = values[2] + " " + plural(values[0]) + " ==> "
							+ convert(CURRENCY_FACTORS, Double.parseDouble(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]))
							+ " " + plural(values[1]);
					JOptionPane.showMessageDialog(null, stringToPrint);
					break;
				case "Distance":
					options = new ArrayList<>(Arrays.asList("Meter", "Foot", "Inch", "Kilometer", "Centimeter"));
					values = input(options, "distance unit");
					stringToPrint = values[2] + " " + plural(values[0]) + " ==> "
							+ convert(DISTANCE_FACTORS, Double.parseDouble(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]))
							+ " " + plural(values[1]);
					JOptionPane.showMessageDialog(null, stringToPrint);
					break;
				case "Temperature":
					options = new ArrayList<>(Arrays.asList("Fahrenheit", "Celsius", "Kelvin"));
					values = input(options, "temperature unit");
					String selection1 = values[0];
					String selection2 = values[1];
					double amount = Double.parseDouble(values[2]);
					double result = 0;
					switch(selection1){
						case "Fahrenheit":
							if(selection2.equals("Celsius")){
								result = (amount - 32) / 1.8;
							} else{
								result = ((amount - 32) / 1.8) + 273.15;
							}
							break;
						case "Celsius":
							if(selection2.equals("Fahrenheit")){
								result = (amount * 1.8) + 32;
							} else{
								result = amount + 273.15;
							}
							break;
						case "Kelvin":
							if(selection2.equals("Fahrenheit")){
								result = ((amount - 273.15) * 1.8) + 32;
							} else{
								result = amount - 273.15;
							}
							break;
					}
					JOptionPane.showMessageDialog(null, amount + " " + plural(selection1) + " ==> "
						+ result + " " + plural(selection2));
					break;
				case "Number Systems":
					options = new ArrayList<>(Arrays.asList("Decimal", "Binary", "Hexadecimal", "Octal"));
					values = input(options, "number system");
					selection1 = values[0];
					selection2 = values[1];
					String strAmount = values[2];
					try{
						amount = Double.parseDouble(values[2]);
					}
					catch(NumberFormatException e){
						amount = 0;
					}
					String num = "";
					switch(selection1){
						case "Decimal":
							if(selection2.equals("Binary")){
								num = Integer.toBinaryString((int) amount);
							}
							else if(selection2.equals("Hexadecimal")){
								num = Integer.toHexString((int) amount);
							}
							else{
								num = Integer.toOctalString((int) amount);
							}
							break;
						case "Binary":
							if(selection2.equals("Decimal")){
								num = String.valueOf(Integer.parseInt(strAmount, 2));
							}
							else if(selection2.equals("Hexadecimal")){
								int decimal = Integer.parseInt(strAmount, 2);
								num = Integer.toString(decimal, 16);
							}
							else{
								int decimal = Integer.parseInt(strAmount, 2);
								num = Integer.toString(decimal, 8);
							}
							break;
						case "Hexadecimal":
							if(selection2.equals("Decimal")){
								num = String.valueOf(Integer.parseInt(strAmount, 16));
							}
							else if(selection2.equals("Binary")){
								int decimal = Integer.parseInt(strAmount, 16);
								num = Integer.toString(decimal, 2);
							}
							else{
								int decimal = Integer.parseInt(strAmount, 16);
								num = Integer.toString(decimal, 8);
							}
							break;
						case "Octal":
							if(selection2.equals("Decimal")){
								num = String.valueOf(Integer.parseInt(strAmount, 8));
							}
							else if(selection2.equals("Binary")){
								int decimal = Integer.parseInt(strAmount, 8);
								num = Integer.toString(decimal, 2);
							}
							else{
								int decimal = Integer.parseInt(strAmount, 8);
								num = Integer.toString(decimal, 16);
							}
							break;
						default:
							System.out.println("Number systems problem");
					}
					JOptionPane.showMessageDialog(null, "Result = " + num);
					break;
				case "Weight":
					options = new ArrayList<>(Arrays.asList("Pound", "Ounce", "Gram", "Kilogram"));
					values = input(options, "weight unit");
					stringToPrint = values[2] + " " + plural(values[0]) + " ==> "
							+ convert(WEIGHT_FACTORS, Double.parseDouble(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]))
							+ " " + plural(values[1]);
					JOptionPane.showMessageDialog(null, stringToPrint);
					break;
				case "Volume":
					options = new ArrayList<>(Arrays.asList("Liter", "Milliliter", "Fluid Ounce", "Cup",
							"Gallon", "Quart", "Pint", "Tablespoon", "Teaspoon"));
					values = input(options, "volume unit");
					stringToPrint = values[2] + " " + plural(values[0]) + " ==> "
							+ convert(VOLUME_FACTORS, Double.parseDouble(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]))
							+ " " + plural(values[1]);
					JOptionPane.showMessageDialog(null, stringToPrint);
					break;
				default:
					break;
			}
		}
	}

	public static String plural(String str){
		switch(str){
			case "Inch":
				return "inches";
			case "Foot":
				return "feet";
			case "Kelvin":
				return "Kelvin";
			case "Fahrenheit":
				return "degrees Fahrenheit";
			case "Celsius":
				return "degrees Celsius";
			default:
				return str + "s";
		}
	}

	public static String enterNumberFormat(String str){
		switch(str){
			case "Binary":
				return "Enter binary number";
			case "Decimal":
				return "Enter decimal number";
			case "Hexadecimal":
				return "Enter hexadecimal number";
			case "Octal":
				return "Enter octal number";
			default:
				return "Enter number of " + plural(str);
		}
	}

	public static String[] input(ArrayList<String> options, String conversion){
		String selection1 = JOptionPane.showInputDialog(null,
				"Choose first " + conversion, "Convert!", JOptionPane.PLAIN_MESSAGE,
				null, options.toArray(), options.get(0)).toString();
		int selectionIndex1 = options.indexOf(selection1);
		options.remove(selection1);
		String selection2 = JOptionPane.showInputDialog(null,
				"Choose second " + conversion, "Menu", JOptionPane.PLAIN_MESSAGE,
				null, options.toArray(), options.get(0)).toString();
		options.add(selectionIndex1, selection1);
		int selectionIndex2 = options.indexOf(selection2);
		System.out.println("Converting " + selection1 + " to " + selection2);
		String amount = JOptionPane.showInputDialog(enterNumberFormat(selection1));
		return new String[]{selection1, selection2, amount, String.valueOf(selectionIndex1), String.valueOf(selectionIndex2)};
	}

	public static String convert(double[][] factors, double value1, int index1, int index2){
		return String.format("%.2f", value1 * factors[index1][index2]);
	}
}
