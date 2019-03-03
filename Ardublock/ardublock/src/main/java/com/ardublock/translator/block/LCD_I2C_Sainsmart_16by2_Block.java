package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LCD_I2C_Sainsmart_16by2_Block extends TranslatorBlock {
	
	public LCD_I2C_Sainsmart_16by2_Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	//@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("LiquidCrystal_I2C.h");
		translator.addDefinitionCommand(             "//SDA A4 , SCL A5\n");
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(3);
		String I2C_addr = "0x"+tb.toCode();
		translator.addDefinitionCommand("LiquidCrystal_I2C lcd_I2C_" + I2C_addr + "( " + I2C_addr + " , 16 , 2);");
		translator.addSetupCommand("lcd_I2C_" + I2C_addr + ".init();");
		translator.addSetupCommand("lcd_I2C_" + I2C_addr + ".backlight();");
		
		String ret="";
		
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		String lineIndex = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(2);
		String wordIndex = tb.toCode();
		ret += "lcd_I2C_" + I2C_addr + ".setCursor(" + wordIndex + "-1, " + lineIndex + "-1);\n";
		
		tb = this.getRequiredTranslatorBlockAtSocket(0,"lcd_I2C_" + I2C_addr + ".print(",");\n");		
		String retString ="";
		if (tb != null) {
			retString = tb.toCode();
		}
		ret += retString;
//		ret += "lcd_I2C_" + I2C_addr + ".print(" + tb.toCode() + ");\n";
		
		return ret;
		
	}
	
}