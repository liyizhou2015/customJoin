// Not now used by standard blocks. Retained as long as it is referenced by legacy blocks.
package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class RomeoMotorBlock extends TranslatorBlock
{
	public static final String MOTOR_DEFINITION = ""
			+ "void setMotors(int m1, int m2, int spd) {\n"
			+ "  spd=constrain(spd, -255, 255);\n"
			+ "  if (spd >= 0) {\n"
			+ "    analogWrite(m1, spd);\n"
			+ "    analogWrite(m2, 0);\n"
			+ "  } else {\n"
			+ "    analogWrite(m1, 0);\n"
			+ "    analogWrite(m2, -spd);\n"
			+ "  }\n"
			+ "}\n";
	public RomeoMotorBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String pinA="";
		String pinN="";
		pinA=translatorBlock.toCode();
		translatorBlock=this.getRequiredTranslatorBlockAtSocket(1);
		pinN=translatorBlock.toCode();
		
		translator.addDefinitionCommand(MOTOR_DEFINITION);
		translator.addSetupCommand("analogWrite("+pinA+", 0);");
		translator.addSetupCommand("analogWrite("+pinN+", 0);");
		
		String ret ="";
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		String spd=translatorBlock.toCode();
		
		ret+="setMotors(" + pinA + "," + pinN+ "," + spd + ");\n";
		
		return ret;
	}

}