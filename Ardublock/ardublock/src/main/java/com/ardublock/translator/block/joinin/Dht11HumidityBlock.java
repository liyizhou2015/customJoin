// Not now used by standard blocks. Retained as long as it is referenced by legacy blocks.
package com.ardublock.translator.block.joinin;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Dht11HumidityBlock extends TranslatorBlock
{

	public Dht11HumidityBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		
		String pinNumber = translatorBlock.toCode();
		String dht11Name = "dht11_pin_" + pinNumber;
		
		translator.addHeaderFile("HqcDht11.h");
		translator.addDefinitionCommand("Dht11 " + dht11Name + "(" + pinNumber + ");\n");
		translator.addSetupCommand(dht11Name + ".init();\n");
		
		String ret = dht11Name + ".getHumidity()";
		
		return codePrefix + ret + codeSuffix;
	}

}
