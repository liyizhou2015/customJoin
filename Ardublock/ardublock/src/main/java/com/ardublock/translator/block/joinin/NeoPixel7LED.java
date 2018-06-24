package com.ardublock.translator.block.joinin;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class NeoPixel7LED extends TranslatorBlock
{
	//the type of neopixel ws2812 5050
	public NeoPixel7LED(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		translator.addHeaderFile("Adafruit_NeoPixel.h");
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);//pin
		translator.addDefinitionCommand("#define PIN " + tb.toCode());
		
		tb = this.getRequiredTranslatorBlockAtSocket(1);//max LED
		translator.addDefinitionCommand("#define MAX_LED " + tb.toCode());
		translator.addDefinitionCommand("#define ADD true");
		String instanceName = "strip_" + Math.random()*100;
		translator.addDefinitionCommand("Adafruit_NeoPixel " + instanceName + " = Adafruit_NeoPixel( MAX_LED, PIN, NEO_RGB + NEO_KHZ800 );");
		
		String ret = "";
		tb = this.getRequiredTranslatorBlockAtSocket(2);//brightness
		int x =Integer.parseInt(tb.toCode());
		x = x < 0 ? 0 : x;
		x = x > 255 ? 255 : x;
		ret = instanceName + ".setBrightness(" + x + ");\n";
		
		tb = this.getRequiredTranslatorBlockAtSocket(3);
		String green = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(4);
		String red = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(5);
		String blue = tb.toCode();
		ret += ;
		
		return codePrefix + ret + codeSuffix;
	} 

}


