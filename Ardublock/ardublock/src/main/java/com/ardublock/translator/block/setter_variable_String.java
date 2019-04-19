package com.ardublock.translator.block;

import java.util.ResourceBundle;
import java.util.Random;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class setter_variable_String extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public setter_variable_String(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		if (!(tb instanceof variable_String)) {
			throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		String ret = "";
		String buffer = "buffer"+String.valueOf((int)(Math.random()*1000));
		ret += "String " + buffer + " = \"\";\n";
		ret += tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(1,"+","");
		ret = ret + " = " + buffer  + tb.toCode() + ";\n";
		
//		ret += " = " + tb.toCode().replace("\"\"", "") + ";\n";
		return ret;
	}

}
