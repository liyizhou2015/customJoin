package com.ardublock.translator.block.joinin;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableNumberBlock;

public class AnaloginputForVar extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public AnaloginputForVar(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public static final String ARDUBLOCK_ANALOG_READ_DEFINE =
			/* If the pin was previously OUTPUT and is **quickly** analog read, the charge on the pin
			 *  and the lack of "settling time" does affect the likely result of analogRead.
			 * Always introducing a delay to settle the pin is not done here as it is overwhelmingly
			 *   unnecessary, too slow to do every time and complicated to detect if it is actually needed.
			 * If this needs to be done then implement https://github.com/arduino/Arduino/issues/4606
			 *  and change to INPUT with a 10ms delay to settle only if the pinMode must be changed.
			 *  Hide the ability to make Analog pins OUTPUT at the end of any pinLists!!!  */
			"int __ardublockAnalogRead(int pinNumber)\n" + 
			"{\n" +
			"pinMode(pinNumber, INPUT);\n" + 
			"return analogRead(pinNumber);\n" + 
			"}\n\n";
	
	
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		if (!(tb instanceof VariableNumberBlock)) {
			throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		}
		String ret = tb.toCode();
		translator.addDefinitionCommand(ARDUBLOCK_ANALOG_READ_DEFINE);
		
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + " = __ardublockAnalogRead(" + tb.toCode() + ");\n";
		return ret;
	}

}
