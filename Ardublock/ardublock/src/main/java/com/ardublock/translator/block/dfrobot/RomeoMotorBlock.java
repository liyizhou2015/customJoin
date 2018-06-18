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
//		translator.addOutputPin(pinA);
//		translator.addOutputPin(pinN);
		translator.addSetupCommand("analogWrite("+pinA+", 0);");
		translator.addSetupCommand("analogWrite("+pinN+", 0);");
		
		String ret ="";
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		int spd= Integer.parseInt(translatorBlock.toCode());
		if(spd > 255){
			spd = 255;
		}else if(spd < -255){
			spd=-255;
		};
		ret+="analogWrite(" + pinA + "," + (spd >= 0 ? spd : 0 ) + ");\n";
		ret+="analogWrite(" + pinN + "," + (spd >= 0 ? 0 : -spd ) + ");\n";
		return ret;
	}

}