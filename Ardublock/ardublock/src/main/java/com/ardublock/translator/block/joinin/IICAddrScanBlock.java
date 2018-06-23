package com.ardublock.translator.block.joinin;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.VariableNumberBlock;

public class IICAddrScanBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public static final String SETUPCONFIG=""
			+ "Wire.begin();\n"
			+ "Serial.begin(9600);\n"
			+ "Serial.println(\"\\nI2C Scanner\");\n";
	
	public IICAddrScanBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addHeaderFile("Wire.h");
		translator.addSetupCommand(SETUPCONFIG);
		
		String ret=
				"byte error, address;\n"
				+ "int nDevices;\n"
				+ "Serial.println(\"Scanning...\");\n"
				+ "nDevices = 0;\n"
				+ "for (address = 1; address < 127; address++ ){\n"
				+ "// The i2c_scanner uses the return value of\n"
				+ "// the Write.endTransmisstion to see if\n"
				+ "// a device did acknowledge to the address.\n"
				+ "Wire.beginTransmission(address);\n"
				+ "error = Wire.endTransmission();\n"
				+ "if (error == 0){\n"
				+ "  Serial.print(\"I2C device found at address 0x\");\n"
				+ "  if (address < 16)\n"
				+ "    Serial.print(\"0\");\n"
				+ "  Serial.print(address, HEX);\n"
				+ "  Serial.println(\" !\");\n"
				+ "  nDevices++;\n"
				+ "}else if (error == 4){\n"
				+ "  Serial.print(\"Unknow error at address 0x\");\n"
				+ "  if (address < 16)\n"
				+ "    Serial.print(\"0\");\n"
				+ "  Serial.println(address, HEX);\n"
				+ "}\n"
				+ "}\n"
				+ "if (nDevices == 0)\n"
				+ "Serial.println(\"No I2C devices found\\n\");\n"
				+ "else\n"
				+ "Serial.println(\"done\\n\");\n"
				+ "delay(5000); // wait 5 seconds for next scan  \n";
		
		
		return ret;
	}

}
