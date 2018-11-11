package com.ardublock.translator.block.joinin;//jq6500 mp3 player

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Mp3Player extends TranslatorBlock
{
	public static final String ARDUBLOCK_DIGITAL_WRITE_DEFINE = 
			"void playMp3(int pinNumber, int index)\n"
			+ "{\n"
			+ "pinMode(pinNumber, OUTPUT);\n"
			+ "digitalWrite(pinNumber, 1);\n"
			+ "delay(100);\n"
			+ "digitalWrite(pinNumber,0);\n"
			+ "}\n";
	
	public Mp3Player(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);//pin
		translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_WRITE_DEFINE);
		String ret = "playMp3(";
		ret = ret + tb.toCode();
		ret = ret + ", ";
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + tb.toCode();
		ret = ret + ");\n";
		return ret;
	} 
}
