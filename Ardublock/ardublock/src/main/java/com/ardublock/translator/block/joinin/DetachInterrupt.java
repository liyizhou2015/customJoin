package com.ardublock.translator.block.joinin;
import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableNumberBlock;

public class DetachInterrupt extends TranslatorBlock
{
	
	public DetachInterrupt(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String pin = translatorBlock.toCode();
		translator.addSetupCommand("pinMode( " + pin + " , INPUT );");
//
//		String mode = this.getRequiredTranslatorBlockAtSocket(1).toCode();
//		String funcName = this.getRequiredTranslatorBlockAtSocket(2).toCode();
//		
		String ret = "";
//		 attachInterrupt(digitalPinToInterrupt(2),attachInterrupt_fun_2,CHANGE);

		ret += "detachInterrupt(digitalPinToInterrupt(" 
		+ pin 
		+ "));\n";

//		translator.addDefinitionCommand(ultraSonicFunction);

		return ret;
	}
	
}
