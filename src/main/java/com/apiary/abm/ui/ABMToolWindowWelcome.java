package com.apiary.abm.ui;

import com.apiary.abm.utility.Utils;
import com.apiary.abm.view.ImageButton;
import com.apiary.abm.view.JBackgroundPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


public class ABMToolWindowWelcome extends JFrame
{
	private ToolWindow mToolWindow;


	public ABMToolWindowWelcome(ToolWindow toolWindow)
	{
		mToolWindow = toolWindow;
		mToolWindow.getContentManager().removeAllContents(true);

		Utils.trackPage("Welcome screen");

		initLayout();
	}


	private void initLayout()
	{
		final ResourceBundle messages = ResourceBundle.getBundle("values/strings");

		// create UI
		final JBackgroundPanel myToolWindowContent = new JBackgroundPanel("drawable/img_background.png", JBackgroundPanel.JBackgroundPanelType.BACKGROUND_REPEAT);
		final ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		final Content content = contentFactory.createContent(myToolWindowContent, "", false);
		mToolWindow.getContentManager().removeAllContents(true);
		mToolWindow.getContentManager().addContent(content);

		// MIGLAYOUT ( params, columns, rows)
		// insets TOP LEFT BOTTOM RIGHT
		myToolWindowContent.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow, center]", "[fill,top][fill, grow][fill,bottom]"));

		final JBackgroundPanel topPanel = new JBackgroundPanel("drawable/img_box_top.png", JBackgroundPanel.JBackgroundPanelType.PANEL);
		final JPanel middlePanel = new JPanel();
		final JBScrollPane middleScrollPanel = new JBScrollPane(middlePanel, JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JBScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		final JBackgroundPanel bottomPanel = new JBackgroundPanel("drawable/img_box_bottom.png", JBackgroundPanel.JBackgroundPanelType.PANEL);

		topPanel.setMinimumSize(new Dimension(0, Utils.reDimension(90)));
		bottomPanel.setMinimumSize(new Dimension(0, Utils.reDimension(90)));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(90)));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(90)));

		// add elements
		topPanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(20) + " " + Utils.reDimension(20) + " " + Utils.reDimension(20) + ", flowy, fillx, filly", "[fill, grow]", "[fill]"));
		middlePanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(15) + " 0 " + Utils.reDimension(15) + ", flowy, fillx, filly", "[grow, center]", "[][]"));
		bottomPanel.setLayout(new MigLayout("insets " + Utils.reDimension(18) + " 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));

		topPanel.setOpaque(false);
		middlePanel.setOpaque(false);
		middleScrollPanel.setOpaque(false);
		middleScrollPanel.getViewport().setOpaque(false);
		middleScrollPanel.setBorder(BorderFactory.createEmptyBorder());
		middleScrollPanel.getVerticalScrollBar().setUnitIncrement(15);
		bottomPanel.setOpaque(false);

		myToolWindowContent.add(topPanel);
		myToolWindowContent.add(middleScrollPanel);
		myToolWindowContent.add(bottomPanel);

		// name
		final JLabel nameText = new JLabel("<html><center>" + messages.getString("welcome_name") + "</center></html>");
		nameText.setForeground(Color.WHITE);
		nameText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_LARGE)));
		nameText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(nameText);


		final JEditorPane infoText = new JEditorPane("text/html", "<html><body style=\"font-family: Ariel; font-weight: bold; color: white; font-size:" + Utils.fontSize(Utils.FONT_MEDIUM) + "pt; \"><center>" + messages.getString("welcome_information") + "</center></body></html>");
		infoText.setOpaque(false);
		infoText.setHighlighter(null);
		infoText.setEditable(false);
		infoText.addHyperlinkListener(new HyperlinkListener()
		{
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e)
			{
				if(e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) try
				{
					Desktop.getDesktop().browse(e.getURL().toURI());
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				}
				catch(URISyntaxException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		middlePanel.add(infoText);

		// version
		final JLabel versionText = new JLabel("<html><center>" + messages.getString("welcome_version") + "</center></html>");
		versionText.setForeground(Color.WHITE);
		versionText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		versionText.setHorizontalAlignment(SwingConstants.CENTER);
		middlePanel.add(versionText);

		// connect button
		final ImageButton button = new ImageButton();
		button.setImage("drawable/img_button_start.png");
		button.setSize(Utils.reDimension(70), Utils.reDimension(70));

		button.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				new ABMToolWindowConnect(mToolWindow);
			}


			public void mousePressed(MouseEvent e)
			{
				button.setImage("drawable/img_button_start_pressed.png");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}


			public void mouseReleased(MouseEvent e)
			{
				button.setImage("drawable/img_button_start.png");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}
		});

		bottomPanel.add(button);
	}
}
