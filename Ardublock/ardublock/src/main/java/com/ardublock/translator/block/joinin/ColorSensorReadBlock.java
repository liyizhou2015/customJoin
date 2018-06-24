package com.ardublock.translator.block.joinin;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ColorSensorReadBlock extends TranslatorBlock
{
	private static final String CONFIG=""
			+ "int getColorSensorRGB(int color){\n"
			+ "uint16_t clear, red, green, blue;\n"
			+ "tcs.setInterrupt(false);\n"
			+ "tcs.getRawData(&red, &green, &blue, &clear);\n"
			+ "tcs.setInterrupt(true);\n"
			+ "delay(50);\n"
			+ "tcs.setInterrupt(true);\n"
			+ "uint32_t sum = clear;\n"
			+ "float r, g, b;\n"
			+ "r = red; r /= sum;\n"
			+ "g = green; g /= sum;\n"
			+ "b = blue; b /= sum;\n"
			+ "r *= 256; g *= 256; b *= 256;\n"
			+ "switch(color){\n"
			+ "case 0:\n"
			+ "  return int(r);\n"
			+ "case 1:\n"
			+ "  return int(g);\n"
			+ "case 2:"
			+ "  return int(b);\n"
			+ "default:"
			+ "  return 0;\n"
			+ "}\n"
			+ "}\n";
	
	public ColorSensorReadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{

		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);

		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("Adafruit_TCS34725.h");
		translator.addDefinitionCommand("Adafruit_TCS34725 tcs = Adafruit_TCS34725(TCS34725_INTEGRATIONTIME_50MS, TCS34725_GAIN_4X);\n");
		translator.addDefinitionCommand(CONFIG);
		translator.addSetupCommand("tcs.begin();\n");
		
		String ret="";
		ret += "getColorSensorRGB(" + tb.toCode() + ")";
		
		return codePrefix + ret + codeSuffix;
	} 

}


