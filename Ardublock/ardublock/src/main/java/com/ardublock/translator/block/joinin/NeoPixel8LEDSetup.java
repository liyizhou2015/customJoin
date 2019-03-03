package com.ardublock.translator.block.joinin;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class NeoPixel8LEDSetup extends TranslatorBlock
{
	//the type of neopixel ws2812 5050
	public NeoPixel8LEDSetup(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		translator.addHeaderFile("Adafruit_NeoPixel.h");
		
		
		String instanceName = this.getRequiredTranslatorBlockAtSocket(0).toCode();	//instance name
		instanceName = instanceName.substring(1, instanceName.length()-1);
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(1);			//pin
		translator.addDefinitionCommand("#define " + instanceName + "_PIN " + tb.toCode());
		
		tb = this.getRequiredTranslatorBlockAtSocket(3);							//max LED
		translator.addDefinitionCommand("#define " + instanceName + "_MAX_LED " + tb.toCode());
		translator.addDefinitionCommand("#define ADD true");
//		String instanceName = "strip" + (int)(Math.random()*1000+1);
		
		translator.addDefinitionCommand("Adafruit_NeoPixel " + instanceName + " = Adafruit_NeoPixel( " + instanceName + "_MAX_LED, " + instanceName + "_PIN, NEO_RGB + NEO_KHZ800 );");
			
		tb = this.getRequiredTranslatorBlockAtSocket(2);//brightness
		int x =Integer.parseInt(tb.toCode());
		x = x < 0 ? 0 : x; 
		x = x > 255 ? 255 : x;
		
		translator.addSetupCommand(instanceName + ".setBrightness(" + x + ");\n");
		translator.addSetupCommand(instanceName + ".begin();\n");
		translator.addSetupCommand(instanceName + ".show();\n");
		
//		tb = this.getRequiredTranslatorBlockAtSocket(3);
//		String green = tb.toCode();
//		tb = this.getRequiredTranslatorBlockAtSocket(4);
//		String red = tb.toCode();
//		tb = this.getRequiredTranslatorBlockAtSocket(5);
//		String blue = tb.toCode();
//		
//		tb = this.getRequiredTranslatorBlockAtSocket(6);//which led
//		
		String ret = "";
//		
//		ret+=instanceName + ".setPixelColor(" + tb.toCode() + "-1 , " + instanceName + ".Color(" + red + "," + green + "," + blue + "));\n"; //	 red    green   blue
//
//		ret += instanceName + ".show();\n";
		
		return ret;
	} 

}


