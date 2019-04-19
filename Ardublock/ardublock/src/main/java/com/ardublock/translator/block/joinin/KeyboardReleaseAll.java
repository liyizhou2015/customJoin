
package com.ardublock.translator.block.joinin;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class KeyboardReleaseAll extends TranslatorBlock {
	
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

	public KeyboardReleaseAll(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
//		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		translator.addDefinitionCommand("#include <Keyboard.h>");
		String ret = "Keyboard.releaseAll();";
		return codePrefix + ret + codeSuffix;
	}

}
