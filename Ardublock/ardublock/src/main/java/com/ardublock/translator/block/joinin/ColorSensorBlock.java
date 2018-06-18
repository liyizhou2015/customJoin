package com.ardublock.translator.block.joinin;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.TranslatorBlock;
public class ColorSensorBlock extends TranslatorBlock
{
	public static final String DEFINITION_CONFIG =
			"#define S0     6\n" + 
			"#define S1     5\n" +
			"#define S2     4\n" +
			"#define S3     3\n" +		
			"#define OUT    2\n" +
			"#define LED    7\n" +
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
			+ "  pinMode(LED, OUTPUT);\n"
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
			+ "void TSC_Count()"
			+ "{"
			+ "  g_count ++ ;"
			+ "}"
			+ "void TSC_Callback()"
			+ "{"
			+ "  switch (g_flag)"
			+ "  {"
			+ "    case 0:"
			+ "    Serial.println(\"->WB Start\");"
			+ "    TSC_WB(LOW, LOW);"
			+ "    break;"
			+ "    case 1:"
			+ "//  Serial.print(\"->Frequency R=\");"
			+ "//  Serial.println(g_count);"
			+ "    g_array[0] = g_count;"
			+ "    TSC_WB(HIGH, HIGH);"
			+ "    break;"
			+ "  case 2:"
			+ "//   Serial.print(\"->Frequency G=\");"
			+ "//   Serial.println(g_count);"
			+ "    g_array[1] = g_count;"
			+ "    TSC_WB(LOW, HIGH);"
			+ "  case 3:"
			+ "//   Serial.print(\"->Frequency B=\");"
			+ "//   Serial.println(g_count);"
			+ "    Serial.println(\"->WB End\");"
			+ "    g_array[2] = g_count;"
			+ "    TSC_WB(HIGH, LOW);"
			+ "    break;"
			+ "  default:"
			+ "    g_count = 0;"
			+ "    break;"
			+ "  }"
			+ "}"
			
			+ "void TSC_WB(int Level0, int Level1)"
			+ "{"
			+ "  g_count = 0;"
			+ "  g_flag ++;"
			+ "  TSC_FilterColor(Level0, Level1); "
			+ "  Timer1.setPeriod(1000000);"
			+ "}";
	public static final String SETUP_CONFIG =""
			+ "TSC_Init();"
			+ "Serial.begin(9600); //启动串行通信"
			+ "Timer1.initialize();   // defaulte is 1s"
			+ "Timer1.attachInterrupt(TSC_Callback); //设置定时器1的中断，中断调用函数为TSC_Callback()"
			+ "//设置TCS3200输出信号的上跳沿触发中断，中断调用函数为TSC_Count()"
			+ "attachInterrupt(0, TSC_Count, RISING);"
			+ "digitalWrite(LED, HIGH);//点亮LED灯"
			+ "delay(4000); //延时4s，以等待被测物体红、绿、蓝三色在1s内的TCS3200输出信号脉冲计数"
			+ "//通过白平衡测试，计算得到白色物体RGB值255与1s内三色光脉冲数的RGB比例因子"
			+ "g_SF[0] = 255.0 / g_array[0];    //红色光比例因子"
			+ "g_SF[1] = 255.0 / g_array[1] ;   //绿色光比例因子"
			+ "g_SF[2] = 255.0 / g_array[2] ;   //蓝色光比例因子"
			+ "//打印白平衡后的红、绿、蓝三色的RGB比例因子"
			+ "//  Serial.println(g_SF[0], 5);"
			+ "//  Serial.println(g_SF[1], 5);"
			+ "//  Serial.println(g_SF[2], 5);"
			+ "//红、绿、蓝三色光分别对应的1s内TCS3200输出脉冲数乘以相应的比例因子就是RGB标准值"
			+ "//打印被测物体的RGB值"
			+ "//  for (int i = 0; i < 3; i++)"
			+ "//  Serial.println(int(g_array[i] * g_SF[i]));";
	public ColorSensorBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		translator.addHeaderFile("TimerOne.h");
		
		translator.addDefinitionCommand(DEFINITION_CONFIG);
		
		translator.addSetupCommand(SETUP_CONFIG);
		
//		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
//
//		translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_READ_DEFINE);
//		String ret = "__ardublockDigitalRead(";
		
//		ret = ret + translatorBlock.toCode();
//		ret = ret + ")";
		return "";
	} 

}
