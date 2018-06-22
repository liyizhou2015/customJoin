package com.ardublock.translator.block.joinin;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableNumberBlock;

public class Tm1637DisplayBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public Tm1637DisplayBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		String CLK=tb.toCode();
		
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		String DIO=tb.toCode();
		
		String TM1637CONFIG =
				"TM1637Display tm1637("+CLK+", "+DIO+");\n"
				+ "uint8_t myTM1637Array[4];\n"
				+ "int pointTM1637=0x80;\n";
		String TM1637SETUP=
				"tm1637.setBrightness(2,true);\n";
		
		translator.addHeaderFile("TM1637Display.h");
		translator.addDefinitionCommand(TM1637CONFIG);
		translator.addSetupCommand(TM1637SETUP);
		
		String ret="";
//		ret += "int ";
		tb = this.getRequiredTranslatorBlockAtSocket(2);
		ret += "tm1637.showNumberDecEx(" + tb.toCode() + ",pointTM1637,false,4,0);\n";
		return ret;
	}

}
