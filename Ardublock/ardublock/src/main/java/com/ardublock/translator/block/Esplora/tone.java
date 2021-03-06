package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class tone extends TranslatorBlock {

	public tone (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	//@Override
			public String toCode() throws SocketNullException, SubroutineNotDeclaredException
			{
				
				String Tone;
							
				TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
				Tone = translatorBlock.toCode();
				
				translator.addHeaderFile("Esplora.h");
				String ret = "Esplora.tone("+Tone+");\n";
				
				return ret;
					
			}
	
}
