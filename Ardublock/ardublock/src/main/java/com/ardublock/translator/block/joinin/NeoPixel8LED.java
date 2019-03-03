package com.ardublock.translator.block.joinin;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class NeoPixel8LED extends TranslatorBlock
{
	//the type of neopixel ws2812 5050
	public NeoPixel8LED(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
//		translator.addHeaderFile("Adafruit_NeoPixel.h");
//		
//		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);//pin
//		translator.addDefinitionCommand("#define PIN " + tb.toCode());
//		
//		tb = this.getRequiredTranslatorBlockAtSocket(1);//max LED
//		translator.addDefinitionCommand("#define MAX_LED " + tb.toCode());
//		translator.addDefinitionCommand("#define ADD true");
//		String instanceName = "strip" + (int)(Math.random()*1000+1);
//		translator.addDefinitionCommand("Adafruit_NeoPixel " + instanceName + " = Adafruit_NeoPixel( MAX_LED, PIN, NEO_RGB + NEO_KHZ800 );");
		TranslatorBlock tb;
		String instanceName = this.getRequiredTranslatorBlockAtSocket(0).toCode();	//instance name
		instanceName = instanceName.substring(1, instanceName.length()-1);
//		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(1);//brightness
//		int x =Integer.parseInt(tb.toCode());
//		x = x < 0 ? 0 : x;
//		x = x > 255 ? 255 : x;
//		
//		translator.addSetupCommand(instanceName + ".setBrightness(" + x + ");\n");
//		translator.addSetupCommand(instanceName + ".begin();\n");
//		translator.addSetupCommand(instanceName + ".show();\n");
		
		tb = this.getRequiredTranslatorBlockAtSocket(2);
		String green = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		String red = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(3);
		String blue = tb.toCode();
		
		tb = this.getRequiredTranslatorBlockAtSocket(4);//which led
		
		String ret = "";
		
		
		ret += instanceName + ".setPixelColor(" + tb.toCode() + "-1 , " + instanceName + ".Color(" +  green+ "," + red + "," + blue + "));\n"; //	 red    green   blue

		ret += instanceName + ".show();\n";
		
		return ret;
	} 

}


