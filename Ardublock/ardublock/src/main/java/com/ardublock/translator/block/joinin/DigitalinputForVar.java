package com.ardublock.translator.block.joinin;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableNumberBlock;

public class DigitalinputForVar extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public DigitalinputForVar(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public static final String ARDUBLOCK_DIGITAL_READ_DEFINE =
			/* The charge left on the pin if it was previously OUTPUT does affect
			 *  the likely result of digitalRead **if the pin is floating**.
			 * I can find nothing to justify a need for a 'settling period' delay
			 *  if the pin mode has been changed.
			 *  and delay 5ms if the pinMode has changed */
			"boolean __ardublockDigitalRead(int pinNumber)\n" + 
			"{\n" +
			"pinMode(pinNumber, INPUT);\n" + 
			"return digitalRead(pinNumber);\n" + 
			"}\n\n";
	
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		if (!(tb instanceof VariableNumberBlock)) {
			throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		}
		String ret = tb.toCode();
		translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_READ_DEFINE);
		
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + " = __ardublockDigitalRead(" + tb.toCode() + ");\n";
		return ret;
	}

}
