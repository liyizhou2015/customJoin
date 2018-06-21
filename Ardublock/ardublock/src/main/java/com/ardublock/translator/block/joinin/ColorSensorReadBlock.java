package com.ardublock.translator.block.joinin;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ColorSensorReadBlock extends TranslatorBlock
{
	
	public ColorSensorReadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{

		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		
		String ret = "int( g_array[" + tb.toCode() + "] * g_SF[" + tb.toCode() + "] )";
		
		return codePrefix + ret + codeSuffix;
	} 

}


