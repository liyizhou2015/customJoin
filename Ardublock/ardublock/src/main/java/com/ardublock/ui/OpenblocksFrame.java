package com.ardublock.ui;
import processing.app.Editor;

//import processing.app.Preferences;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import java.awt.Cursor;
import java.awt.Insets;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.GridLayout; 

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;


import com.ardublock.ArduBlockToolJOIN;
import com.ardublock.core.Context;
import com.ardublock.ui.listener.ArdublockWorkspaceListener;
import com.ardublock.ui.listener.GenerateCodeButtonListener;
import com.ardublock.ui.listener.NewButtonListener;
import com.ardublock.ui.listener.OpenButtonListener;
import com.ardublock.ui.listener.OpenblocksFrameListener;
import com.ardublock.ui.listener.SaveAsButtonListener;
import com.ardublock.ui.listener.SaveButtonListener;
//import com.ardublock.ui.listener.SwitchButtonListener;

import edu.mit.blocks.controller.WorkspaceController;
import edu.mit.blocks.workspace.Workspace;


public class OpenblocksFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2841155965906223806L;

	private Context context;
	private JFileChooser fileChooser;
	private FileFilter ffilter;
	
	private ResourceBundle uiMessageBundle;
	
	private Cursor cursorWait=new Cursor(Cursor.WAIT_CURSOR);
	private Cursor cursorHand=new Cursor(Cursor.HAND_CURSOR);
	private Cursor cursorDefault=new Cursor(Cursor.DEFAULT_CURSOR);
	
	private static int theInterfaceVer = 0; // 0:basic 1:advanced
	
	private static String exportSerialName="COM3";
	private static String exportBoardName="uno";
	
	public void addListener(OpenblocksFrameListener ofl)
	{
		context.registerOpenblocksFrameListener(ofl);
	}
	
	public String makeFrameTitle()
	{
		//set window icon
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/com/ardublock/icons/join-in-logo.png"));
	
		this.setIconImage(imageIcon.getImage());
		
		String title = Context.APP_NAME + " " + context.getSaveFileName();
		if (context.isWorkspaceChanged())
		{
			title = title + " *";
		}
		return title;
		
	}
	
	public OpenblocksFrame()
	{
		context = Context.getContext();
		this.setTitle(makeFrameTitle());
		this.setSize(new Dimension(1024, 760));
		this.setLayout(new BorderLayout());
		//put the frame to the center of screen
		this.setLocationRelativeTo(null);
		
		uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
		
		fileChooser = new JFileChooser();
		ffilter = new FileNameExtensionFilter(uiMessageBundle.getString("ardublock.file.suffix"), "abp");
		fileChooser.setFileFilter(ffilter);
		fileChooser.addChoosableFileFilter(ffilter);
		
		initOpenBlocks();
		
		

		Toolkit toolkit = Toolkit.getDefaultToolkit();// 获得窗体工具包
        Dimension screenSize = toolkit.getScreenSize();// 获取屏幕大小
     
        int width = (int) (screenSize.width * 0.65);// 计算窗体新宽度
        
        GraphicsEnvironment graphicsEnvironment=GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle maximumWindowBounds=graphicsEnvironment.getMaximumWindowBounds();
        
		this.setBounds(0,0,width,maximumWindowBounds.height);
	}

	private void initOpenBlocks()
	{
		final Context context = Context.getContext();
		
		/*
		WorkspaceController workspaceController = context.getWorkspaceController();
		JComponent workspaceComponent = workspaceController.getWorkspacePanel();
		*/
		
		final Workspace workspace = context.getWorkspace();
		
		// WTF I can't add worksapcelistener by workspace contrller
		workspace.addWorkspaceListener(new ArdublockWorkspaceListener(this));
		
		final JLabel buttonTooltip = new JLabel();
		
		final JButton newButton = new JButton();
		newButton.setIcon(new ImageIcon(this.getClass().getResource("/com/ardublock/icons/new.png")));
		newButton.setContentAreaFilled(true); 
		newButton.setBackground(Color.LIGHT_GRAY);
		newButton.setPreferredSize(new Dimension(52, 52));
		newButton.setOpaque(false);
		newButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.new"));
		newButton.setHorizontalTextPosition(SwingConstants.CENTER);
		newButton.addActionListener(new NewButtonListener(this));
		
		
		newButton.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {
				newButton.setCursor(cursorHand);
				buttonTooltip.setText(uiMessageBundle.getString("ardublock.ui.new"));
				newButton.setContentAreaFilled(true); 
			}
			public void mouseExited(MouseEvent arg0) {
				buttonTooltip.setText("");
				newButton.setContentAreaFilled(false);
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		
		final JButton saveButton = new JButton();
		saveButton.setIcon(new ImageIcon(this.getClass().getResource("/com/ardublock/icons/save.png")));
		saveButton.setContentAreaFilled(false); 
		saveButton.setBackground(Color.LIGHT_GRAY);
		saveButton.setPreferredSize( new Dimension(52, 52));
		saveButton.setOpaque(false);
		saveButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.save"));
		saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
		saveButton.addActionListener(new SaveButtonListener(this));
		saveButton.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {
				saveButton.setCursor(cursorHand);
				saveButton.setContentAreaFilled(true); 
				buttonTooltip.setText(uiMessageBundle.getString("ardublock.ui.save"));
			}
			public void mouseExited(MouseEvent arg0) {
				saveButton.setContentAreaFilled(false);
				buttonTooltip.setText("");
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
	
		});
		
		final JButton saveAsButton = new JButton();
		saveAsButton.setIcon(new ImageIcon(this.getClass().getResource("/com/ardublock/icons/saveas.png")));
		saveAsButton.setContentAreaFilled(false); 
		saveAsButton.setBackground(Color.LIGHT_GRAY);
		saveAsButton.setPreferredSize( new Dimension(52, 52));
		saveAsButton.setOpaque(false);
		saveAsButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.saveAs"));
		saveAsButton.setHorizontalTextPosition(SwingConstants.CENTER);
		saveAsButton.addActionListener(new SaveAsButtonListener(this));
		saveAsButton.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {
				saveAsButton.setCursor(cursorHand);
				saveAsButton.setContentAreaFilled(true); 
				buttonTooltip.setText(uiMessageBundle.getString("ardublock.ui.saveAs"));
			}
			public void mouseExited(MouseEvent arg0) {
				saveAsButton.setContentAreaFilled(false);
				buttonTooltip.setText("");
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
	
		});
		
		
		final JButton openButton = new JButton();
		openButton.setIcon(new ImageIcon(this.getClass().getResource("/com/ardublock/icons/open.png")));
		openButton.setContentAreaFilled(false); 
		openButton.setBackground(Color.LIGHT_GRAY);
		openButton.setPreferredSize( new Dimension(52, 52));
		openButton.setOpaque(false);
		openButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.load"));
		openButton.setHorizontalTextPosition(SwingConstants.CENTER);
		openButton.addActionListener(new OpenButtonListener(this));
		openButton.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {
				openButton.setCursor(cursorHand);
				openButton.setContentAreaFilled(true); 
				buttonTooltip.setText(uiMessageBundle.getString("ardublock.ui.load"));
			}
			public void mouseExited(MouseEvent arg0) {
				openButton.setContentAreaFilled(false);
				buttonTooltip.setText("");
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
	
		});
		
		final JButton generateButton = new JButton();
		generateButton.setIcon(new ImageIcon(this.getClass().getResource("/com/ardublock/icons/download.png")));
		generateButton.setContentAreaFilled(false); 
		generateButton.setBackground(Color.LIGHT_GRAY);
		generateButton.setPreferredSize( new Dimension(52, 52));
		generateButton.setOpaque(false);
		generateButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.upload"));
		generateButton.setHorizontalTextPosition(SwingConstants.CENTER);
		generateButton.addActionListener(new GenerateCodeButtonListener(this, context));
		generateButton.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0) {
				context.getEditor().handleConfigForAB(exportSerialName,exportBoardName);
			}
			public void mouseEntered(MouseEvent arg0) {
				generateButton.setCursor(cursorHand);
				generateButton.setContentAreaFilled(true); 
				buttonTooltip.setText(uiMessageBundle.getString("ardublock.ui.upload"));
			}
			public void mouseExited(MouseEvent arg0) {
				generateButton.setContentAreaFilled(false);
				buttonTooltip.setText("");
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
	
		});
		
		final JButton serialMonitorButton = new JButton();
		serialMonitorButton.setIcon(new ImageIcon(this.getClass().getResource("/com/ardublock/icons/serial.png")));
		serialMonitorButton.setContentAreaFilled(false); 
		serialMonitorButton.setBackground(Color.LIGHT_GRAY);
		serialMonitorButton.setPreferredSize( new Dimension(52, 52));
		serialMonitorButton.setOpaque(false);
		serialMonitorButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.serialMonitor"));
		serialMonitorButton.setHorizontalTextPosition(SwingConstants.CENTER);
		serialMonitorButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				context.getEditor().handleSerial();
			}
		});
		serialMonitorButton.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {
				serialMonitorButton.setCursor(cursorHand);
				serialMonitorButton.setContentAreaFilled(true);
				buttonTooltip.setText(uiMessageBundle.getString("ardublock.ui.serialMonitor"));
			}
			public void mouseExited(MouseEvent arg0) {
				serialMonitorButton.setContentAreaFilled(false);
				buttonTooltip.setText("");
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
	
		});
//		ardublock.ui.saveImage
		JButton saveImageButton = new JButton(uiMessageBundle.getString("ardublock.ui.saveImage"));
		saveImageButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				Dimension size = workspace.getCanvasSize();
				System.out.println("size: " + size);
				BufferedImage bi = new BufferedImage(2560, 2560, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = (Graphics2D)bi.createGraphics();
				double theScaleFactor = (300d/72d);  
				g.scale(theScaleFactor,theScaleFactor);
				
				workspace.getBlockCanvas().getPageAt(0).getJComponent().paint(g);
				try{
					final JFileChooser fc = new JFileChooser();
					fc.setSelectedFile(new File("ardublock.png"));
					int returnVal = fc.showSaveDialog(workspace.getBlockCanvas().getJComponent());
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
						ImageIO.write(bi,"png",file);
			        }
				} catch (Exception e1) {
					
				} finally {
					g.dispose();
				}
			}
		});

		
		final JButton switchButton = new JButton();
		switchButton.setIcon(new ImageIcon(this.getClass().getResource("/com/ardublock/icons/basicVersion.png")));
		switchButton.setContentAreaFilled(false); 
		switchButton.setBackground(Color.LIGHT_GRAY);
		switchButton.setPreferredSize( new Dimension(52, 52));
		switchButton.setOpaque(false);
//		switchButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.upload"));
		switchButton.setToolTipText("switch");
		switchButton.setHorizontalTextPosition(SwingConstants.CENTER);
//		switchButton.addActionListener(new SwitchButtonListener(this));
		
		switchButton.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0) {
				switchButton.setCursor(cursorWait);
				switchArduBlockInteface();
				
				
				if(theInterfaceVer == 0){
					switchButton.setIcon(new ImageIcon(this.getClass().getResource("/com/ardublock/icons/basicVersion.png")));
				}else{
					switchButton.setIcon(new ImageIcon(this.getClass().getResource("/com/ardublock/icons/advancedVersion.png")));
				}

				switchButton.setCursor(cursorDefault);
			}
			public void mouseEntered(MouseEvent arg0) {
				switchButton.setCursor(cursorHand);
				switchButton.setContentAreaFilled(true); 
//				buttonTooltip.setText(uiMessageBundle.getString("ardublock.ui.upload"));
			}
			public void mouseExited(MouseEvent arg0) {
				switchButton.setContentAreaFilled(false);
				switchButton.setText("");
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
	
		});
		
		//serial list
        JPanel contentPane=new JPanel(); 
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));  
        JLabel label=new JLabel("serial:");  
        contentPane.add(label);  
        
        
        final String items[] = {"com"};   
        
        final DefaultComboBoxModel model = new DefaultComboBoxModel(items);  
        
        JComboBox comboBox=new JComboBox(model);
        
        contentPane.add(comboBox);
        
        comboBox.addPopupMenuListener(new PopupMenuListener(){
        	 @Override
        	 public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        		 model.removeAllElements();
        		 String[] portList=context.getEditor().getSerialPortList();
        		 for(String portName : portList){
        			 model.addElement(portName);
        		 }
    		 }
             public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {  
                 
             }  
             public void popupMenuCanceled(PopupMenuEvent e) {  
             }  
        });
        
        comboBox.addItemListener(new ItemListener(){
        	public void itemStateChanged(ItemEvent e){
        		Object x=e.getItem();
    			exportSerialName=x.toString();
//    			System.out.println("item listener serialPort: "+exportSerialName+" x: "+x.toString());
            }  
        });

        //board list
        JPanel boardListPanel=new JPanel(); 
        boardListPanel.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));  
        JLabel boardListLabel=new JLabel("board:");  
        boardListPanel.add(boardListLabel);  
        final String boardListItems[] = {"uno","pro","nano","leonardo"};   
        final DefaultComboBoxModel boardListModel = new DefaultComboBoxModel(boardListItems);  
        
        JComboBox boardList=new JComboBox(boardListModel);
        
        boardListPanel.add(boardList);
        
        boardList.addItemListener(new ItemListener(){
        	public void itemStateChanged(ItemEvent e){
        		Object x=e.getItem();
    			exportBoardName=x.toString();
//    			System.out.println("item listener board: "+exportBoardName+" x: "+x.toString());
            }  
        });
        
        
		JPanel buttonsLeft = new JPanel();
		JPanel buttonsRight = new JPanel();
		
		buttonsLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonsRight.setLayout(new FlowLayout(FlowLayout.RIGHT));

		buttonsLeft.add(newButton);
		buttonsLeft.add(saveButton);
		buttonsLeft.add(saveAsButton);
		buttonsLeft.add(openButton);
		buttonsLeft.add(generateButton);
		buttonsLeft.add(serialMonitorButton);
		
		buttonsRight.add(boardListPanel);
		buttonsRight.add(contentPane);
		buttonsRight.add(switchButton);
		
		Box box=Box.createHorizontalBox();
		box.add(buttonsLeft);
		box.add(buttonsRight);
		
		box.add(Box.createHorizontalStrut(0));  
		
		JPanel bottomPanel = new JPanel();
		JButton websiteButton = new JButton(uiMessageBundle.getString("ardublock.ui.website"));
		websiteButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
			    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    URL url;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
						url = new URL("http://ardublock.com");
			            desktop.browse(url.toURI());
			        } catch (Exception e1) {
			            e1.printStackTrace();
			        }
			    }
			}
		});
		JLabel versionLabel = new JLabel("v " + uiMessageBundle.getString("ardublock.ui.version"));
		
		bottomPanel.add(saveImageButton);
//		bottomPanel.add(websiteButton);
		bottomPanel.add(versionLabel);

		
		this.add(box, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.SOUTH);
		this.add(workspace, BorderLayout.CENTER);
		
		
	}
	
	public void switchArduBlockInteface(){
		if(theInterfaceVer == 0){
			theInterfaceVer = 1;
			context.switchXML(theInterfaceVer);
			context.resetWorksapce();
		}else{
			theInterfaceVer = 0 ;
			context.switchXML(theInterfaceVer);
			context.resetWorksapce();
		}
		
//		System.out.println("theInterfaceVer: "+theInterfaceVer);
	}
	
	public void doOpenArduBlockFile()
	{
		if (context.isWorkspaceChanged())
		{
			int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.content.open_unsaved"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
			if (optionValue == JOptionPane.YES_OPTION)
			{
				doSaveArduBlockFile();
				this.loadFile();
			}
			else
			{
				if (optionValue == JOptionPane.NO_OPTION)
				{
					this.loadFile();
				}
			}
		}
		else
		{
			this.loadFile();
		}
		this.setTitle(makeFrameTitle());
	}
	
	private void loadFile()
	{
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File savedFile = fileChooser.getSelectedFile();
			if (!savedFile.exists())
			{
				JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.file_not_found"), uiMessageBundle.getString("message.title.error"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
				return ;
			}
			
			try
			{
				this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				context.loadArduBlockFile(savedFile);
				context.setWorkspaceChanged(false);
			}
			catch (IOException e)
			{
				JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.file_not_found"), uiMessageBundle.getString("message.title.error"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
				e.printStackTrace();
			}
			finally
			{
				this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
	
	public boolean doSaveArduBlockFile()
	{
		if (!context.isWorkspaceChanged())
		{
			return true;
		}
		
		String saveString = getArduBlockString();
		
		if (context.getSaveFilePath() == null)
		{
			return chooseFileAndSave(saveString);
		}
		else
		{
			File saveFile = new File(context.getSaveFilePath());
			writeFileAndUpdateFrame(saveString, saveFile);
			return true;
		}
	}

	
	public void doSaveAsArduBlockFile()
	{
		if (context.isWorkspaceEmpty())
		{
			return ;
		}
		
		String saveString = getArduBlockString();
		
		chooseFileAndSave(saveString);
		
	}
	
	private boolean chooseFileAndSave(String ardublockString)
	{
		File saveFile = letUserChooseSaveFile();
		saveFile = checkFileSuffix(saveFile);
		if (saveFile == null)
		{
			return false;
		}
		
		if (saveFile.exists() && !askUserOverwriteExistedFile())
		{
			return false;
		}
		
		writeFileAndUpdateFrame(ardublockString, saveFile);
		return true;
	}
	
	private String getArduBlockString()
	{
		WorkspaceController workspaceController = context.getWorkspaceController();
		return workspaceController.getSaveString();
	}
	
	private void writeFileAndUpdateFrame(String ardublockString, File saveFile) 
	{
		try
		{
			saveArduBlockToFile(ardublockString, saveFile);
			context.setWorkspaceChanged(false);
			this.setTitle(this.makeFrameTitle());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	private File letUserChooseSaveFile()
	{
		int chooseResult;
		chooseResult = fileChooser.showSaveDialog(this);
		if (chooseResult == JFileChooser.APPROVE_OPTION)
		{
			return fileChooser.getSelectedFile();
		}
		return null;
	}
	
	private boolean askUserOverwriteExistedFile()
	{
		int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.content.overwrite"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
		return (optionValue == JOptionPane.YES_OPTION);
	}
	
	private void saveArduBlockToFile(String ardublockString, File saveFile) throws IOException
	{
		context.saveArduBlockFile(saveFile, ardublockString);
		context.setSaveFileName(saveFile.getName());
		context.setSaveFilePath(saveFile.getAbsolutePath());
	}
	
	public void doNewArduBlockFile()
	{
		if (context.isWorkspaceChanged())
		{
			int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.question.newfile_on_workspace_changed"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
			
			switch (optionValue)
			{
            	case JOptionPane.YES_OPTION:
            		doSaveArduBlockFile();
                    //break;
            	case JOptionPane.NO_OPTION:
            		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            		context.resetWorksapce();
            		context.setWorkspaceChanged(false);
            		this.setTitle(this.makeFrameTitle());
            		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            		break;
            	case JOptionPane.CANCEL_OPTION:
                    break;
			}
		}
		else
		{
			// If workspace unchanged just start a new Ardublock
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    		context.resetWorksapce();
    		context.setWorkspaceChanged(false);
    		this.setTitle(this.makeFrameTitle());
    		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
	}
	
	public void doCloseArduBlockFile()
	{
		if (context.isWorkspaceChanged())
		{
			int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.question.close_on_workspace_changed"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
			switch (optionValue)
			{
            	case JOptionPane.YES_OPTION:
            		if (doSaveArduBlockFile())
            		{
            			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            		}
            		else
            		{
            			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            		}
                    break;
            	case JOptionPane.NO_OPTION:
            		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            		break;
            	case JOptionPane.CANCEL_OPTION:
            		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    break;
			}
		}
		else
		{
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
			
	}
	
	
	private File checkFileSuffix(File saveFile)
	{
		String filePath = saveFile.getAbsolutePath();
		if (filePath.endsWith(".abp"))
		{
			return saveFile;
		}
		else
		{
			return new File(filePath + ".abp");
		}
	}
}
