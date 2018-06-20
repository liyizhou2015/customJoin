package com.ardublock.translator.block.joinin;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ColorSensorSetupBlock extends TranslatorBlock
{
	
	public static final String SETUP_CONFIG =""
			+ "TSC_Init();"
//			+ "Serial.begin(9600);"
			+ "Timer1.initialize();"
			+ "Timer1.attachInterrupt(TSC_Callback);"
			+ "attachInterrupt(0, TSC_Count, RISING);"
//			+ "digitalWrite(LED, HIGH);"
			+ "delay(4000);"
			+ "g_SF[0] = 255.0 / g_array[0];"
			+ "g_SF[1] = 255.0 / g_array[1];"
			+ "g_SF[2] = 255.0 / g_array[2];";
	
	public ColorSensorSetupBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String S0 = "6";
		String S1 = "5";
		String S2 = "4";
		String S3 = "3";
		String OUT = "2";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		S0 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		S1 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		S2 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
		S3 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(4);
		OUT = translatorBlock.toCode();
		
//		int LED = 7;
		
		String DEFINITION_CONFIG =
				"#define S0     "+S0+"\n" + 
				"#define S1     "+S1+"\n" +
				"#define S2     "+S2+"\n" +
				"#define S3     "+S3+"\n" +		
				"#define OUT    "+OUT+"\n" +
//				"#define LED    "+LED+"\n" +
				"float g_SF[3];\n"   +
				"int g_count = 0;\n" +
				"int g_array[3];\n"  +
				"int g_flag = 0;\n"  +
				"void TSC_Init()\n"
				+ "{\n"
				+ "  pinMode(S0, OUTPUT);\n"
				+ "  pinMode(S1, OUTPUT);\n"
				+ "  pinMode(S2, OUTPUT);\n"
				+ "  pinMode(S3, OUTPUT);\n"
				+ "  pinMode(OUT, INPUT);\n"
//				+ "  pinMode(LED, OUTPUT);\n"
				+ "  digitalWrite(S0, LOW);\n"
				+ "  digitalWrite(S1, HIGH);\n"
				+ "}\n"
				+"void TSC_FilterColor(int Level01, int Level02)"
				+ "{\n"
				+ "  if (Level01 != 0) Level01 = HIGH;\n"
				+ "  if (Level02 != 0) Level02 = HIGH;\n"
				+ "  digitalWrite(S2, Level01);\n"
				+ "  digitalWrite(S3, Level02);\n"
				+ "}\n"
				+ "void TSC_Count()\n"
				+ "{"
				+ "  g_count ++ ;"
				+ "}"
				+ "void TSC_Callback()\n"
				+ "{"
				+ "  switch (g_flag)"
				+ "  {"
				+ "    case 0:"
//				+ "    Serial.println(\"->WB Start\");"
				+ "    TSC_WB(LOW, LOW);"
				+ "    break;"
				+ "    case 1:\n"
//				+ "    Serial.print(\"->Frequency R=\");\n"
//				+ "    Serial.println(g_count);\n"
				+ "    g_array[0] = g_count;\n"
				+ "    TSC_WB(HIGH, HIGH);\n"
				+ "    break;\n"
				+ "  case 2:\n"
//				+ "    Serial.print(\"->Frequency G=\");\n"
//				+ "    Serial.println(g_count);\n"
				+ "    g_array[1] = g_count;\n"
				+ "    TSC_WB(LOW, HIGH);\n"
				+ "  case 3:\n"
//				+ "    Serial.print(\"->Frequency B=\");\n"
//				+ "    Serial.println(g_count);\n"
//				+ "    Serial.println(\"->WB End\");\n"
				+ "    g_array[2] = g_count;\n"
				+ "    TSC_WB(HIGH, LOW);\n"
				+ "    break;\n"
				+ "  default:\n"
				+ "    g_count = 0;\n"
				+ "    break;\n"
				+ "  }\n"
				+ "}"
				+ "void TSC_WB(int Level0, int Level1)\n"
				+ "{"
				+ "  g_count = 0;"
				+ "  g_flag ++;"
				+ "  TSC_FilterColor(Level0, Level1); "
				+ "  Timer1.setPeriod(1000000);"
				+ "}";
		
		translator.addHeaderFile("TimerOne.h");
		translator.addDefinitionCommand(DEFINITION_CONFIG);
		translator.addSetupCommand(SETUP_CONFIG);
		
		String ret = "delay( 4000 );";
		
		return codePrefix + ret + codeSuffix;
	} 

}


