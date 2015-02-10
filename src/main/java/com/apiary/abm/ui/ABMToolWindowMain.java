package com.apiary.abm.ui;

import com.apiary.abm.entity.BodyObjectEntity;
import com.apiary.abm.entity.BodyVariableEntity;
import com.apiary.abm.entity.ProblemEntity;
import com.apiary.abm.entity.TreeNodeEntity;
import com.apiary.abm.entity.blueprint.ABMEntity;
import com.apiary.abm.entity.blueprint.ActionsEntity;
import com.apiary.abm.entity.blueprint.ExamplesEntity;
import com.apiary.abm.entity.blueprint.ParametersEntity;
import com.apiary.abm.entity.blueprint.RequestEntity;
import com.apiary.abm.entity.blueprint.ResourceGroupsEntity;
import com.apiary.abm.entity.blueprint.ResourcesEntity;
import com.apiary.abm.entity.blueprint.ResponsesEntity;
import com.apiary.abm.enums.TreeNodeTypeEnum;
import com.apiary.abm.enums.VariableEnum;
import com.apiary.abm.renderer.ABMTreeCellRenderer;
import com.apiary.abm.utility.ConfigPreferences;
import com.apiary.abm.utility.Log;
import com.apiary.abm.utility.Network;
import com.apiary.abm.utility.Preferences;
import com.apiary.abm.utility.ProjectManager;
import com.apiary.abm.utility.Utils;
import com.apiary.abm.view.ImageButton;
import com.apiary.abm.view.JBackgroundPanel;
import com.google.gson.Gson;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.PsiClass;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.treeStructure.Tree;

import net.miginfocom.swing.MigLayout;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


public class ABMToolWindowMain extends JFrame
{
	private ToolWindow mToolWindow;
	private JLabel mInformationIcon;
	private final ResourceBundle mMessages = ResourceBundle.getBundle("values/strings");


	public ABMToolWindowMain(ToolWindow toolWindow)
	{
		mToolWindow = toolWindow;
		mToolWindow.getContentManager().removeAllContents(true);

		initLayout();
	}


	private void initLayout()
	{
		Preferences prefs = new Preferences();

		// create UI
		final JBackgroundPanel myToolWindowContent = new JBackgroundPanel("drawable/img_background.png", JBackgroundPanel.JBackgroundPanelType.BACKGROUND_REPEAT);
		final ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		final Content content = contentFactory.createContent(myToolWindowContent, "", false);
		mToolWindow.getContentManager().removeAllContents(true);
		mToolWindow.getContentManager().addContent(content);

		// MIGLAYOUT ( params, columns, rows)
		// insets TOP LEFT BOTTOM RIGHT
		myToolWindowContent.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow]", "[fill,top][fill, grow][fill,bottom]"));

		final JBackgroundPanel topPanel = new JBackgroundPanel("drawable/img_box_top.png", JBackgroundPanel.JBackgroundPanelType.PANEL);
		final JPanel middlePanel = new JPanel();
		final JBScrollPane middleScrollPanel = new JBScrollPane(middlePanel, JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		final JBackgroundPanel bottomPanel = new JBackgroundPanel("drawable/img_box_bottom.png", JBackgroundPanel.JBackgroundPanelType.PANEL);

		topPanel.setMinimumSize(new Dimension(0, Utils.reDimension(90)));
		bottomPanel.setMinimumSize(new Dimension(0, Utils.reDimension(90)));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(90)));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(90)));

		// add elements
		topPanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(20) + " " + Utils.reDimension(20) + " 0, flowx, fillx, filly", "[grow, center][]", "[center, top]"));
		middlePanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(15) + " 0 " + Utils.reDimension(15) + ", flowy, fillx, filly", "[fill, grow]", "[fill, grow]"));
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

		// refresh and analyze blueprint
		ABMEntity object;
		List<TreeNodeEntity> treeNodeList = null;
		try
		{
			String json = Utils.readFileAsString(prefs.getBlueprintJsonTmpFileLocation(), Charset.forName("UTF-8"));
			if(json==null)
			{
				String blueprint = Utils.readFileAsString(Network.refreshBlueprint(), Charset.forName("UTF-8"));
				json = Network.requestJSONFromBlueprint(blueprint);
			}
			object = Utils.parseJsonBlueprint(json);
			if(object.getError()==null) treeNodeList = analyzeBlueprint(object);
			treeNodeList = analyzeTreeNodeList(treeNodeList);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		// information icon
		mInformationIcon = new JLabel();
		mInformationIcon.setOpaque(false);
		mInformationIcon.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(mInformationIcon, "gap " + Utils.reDimension(27) + "px"); // config button gaps (10 + 10) + size (35) = 55 / 2 = 27


		// tree structure
		final JBackgroundPanel middleTreePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		middleTreePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[fill, grow]", "[fill, grow]"));
		middleTreePanel.setOpaque(false);

		final JTree tree = new Tree(initTreeStructure(treeNodeList));
		tree.setRootVisible(false);
		tree.setOpaque(false);
		tree.setCellRenderer(new ABMTreeCellRenderer());

		middleTreePanel.add(tree);
		middlePanel.add(middleTreePanel);

		// refresh button
		final ImageButton button = new ImageButton();
		button.setImage("drawable/img_button_refresh.png");
		button.setSize(Utils.reDimension(70), Utils.reDimension(70));

		button.addMouseListener(new MouseAdapter()
		{
			private boolean progress;


			public void mouseClicked(MouseEvent e)
			{
				if(progress) return;
				progress = true;

				button.setImage("drawable/animation_refresh.gif");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));

				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						ABMEntity object;
						Preferences prefs = new Preferences();
						String blueprint;
						String json = "";
						try
						{
							blueprint = Utils.readFileAsString(Network.refreshBlueprint(), Charset.forName("UTF-8"));

							if(blueprint.equals(""))
								json = Utils.readFileAsString(prefs.getBlueprintTmpFileLocation(), Charset.forName("UTF-8"));
							else json = Network.requestJSONFromBlueprint(blueprint);
						}
						catch(IOException e)
						{
							// todo error
							e.printStackTrace();
						}
						object = Utils.parseJsonBlueprint(json);
						if(object.getError()==null)
						{
							final List<TreeNodeEntity> treeNodeList = analyzeBlueprint(object);

							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									final JTree tree = new Tree(initTreeStructure(analyzeTreeNodeList(treeNodeList)));
									tree.setRootVisible(false);
									tree.setOpaque(false);
									tree.setCellRenderer(new ABMTreeCellRenderer());
									tree.addMouseListener(new MouseAdapter()
									{
										public void mousePressed(MouseEvent e)
										{
											int row = tree.getRowForLocation(e.getX(), e.getY());
											TreePath path = tree.getPathForLocation(e.getX(), e.getY());
											if(row!=-1 && e.getClickCount()==2)
												onTreeNodeDoubleClick((TreeNodeEntity) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject());
										}
									});

									middleTreePanel.removeAll();
									middleTreePanel.add(tree);
									middleTreePanel.validate();
									middleTreePanel.repaint();

									button.setImage("drawable/img_button_refresh.png");
									button.setSize(Utils.reDimension(70), Utils.reDimension(70));

									progress = false;
								}
							});
						}
					}
				});
				t.start();
			}


			public void mousePressed(MouseEvent e)
			{
				if(progress) return;
				button.setImage("drawable/img_button_refresh_pressed.png");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}


			public void mouseReleased(MouseEvent e)
			{
				if(progress) return;
				button.setImage("drawable/img_button_refresh.png");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}
		});
		bottomPanel.add(button);

		// config button
		final ImageButton buttonConfig = new ImageButton();
		buttonConfig.setImage("drawable/img_button_config.png");
		buttonConfig.setSize(Utils.reDimension(35), Utils.reDimension(35));

		buttonConfig.addMouseListener(new MouseAdapter()
		{
			private boolean progress;


			public void mouseClicked(MouseEvent e)
			{
				if(progress) return;
				buttonConfig.setImage("drawable/img_button_config_pressed.png");
				buttonConfig.setSize(Utils.reDimension(35), Utils.reDimension(35));
				progress = true;

				new ABMToolWindowConfiguration(mToolWindow);
			}


			public void mousePressed(MouseEvent e)
			{
				if(progress) return;
				buttonConfig.setImage("drawable/img_button_config_pressed.png");
				buttonConfig.setSize(Utils.reDimension(35), Utils.reDimension(35));
			}


			public void mouseReleased(MouseEvent e)
			{
				if(progress) return;
				buttonConfig.setImage("drawable/img_button_config.png");
				buttonConfig.setSize(Utils.reDimension(35), Utils.reDimension(35));
			}
		});
		topPanel.add(buttonConfig, "right, gap 0px " + Utils.reDimension(10) + "px " + Utils.reDimension(10) + "px 0px ");


		// Tree click listener
		tree.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				int row = tree.getRowForLocation(e.getX(), e.getY());
				TreePath path = tree.getPathForLocation(e.getX(), e.getY());
				if(row!=-1 && e.getClickCount()==2)
					onTreeNodeDoubleClick((TreeNodeEntity) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject());
			}
		});
	}


	private void onTreeNodeDoubleClick(TreeNodeEntity entity)
	{
		// TODO call toolWindows onClick
		if(entity.getTreeNodeType()==TreeNodeTypeEnum.CONFIGURATION_PROBLEM) new ABMToolWindowConfiguration(mToolWindow);
		else if(entity.getTreeNodeType()==TreeNodeTypeEnum.NOT_IMPLEMENTED) new ABMToolWindowImplementationFirst(mToolWindow, entity);
		else if(entity.getTreeNodeType()==TreeNodeTypeEnum.MODIFIED) new ABMToolWindowImplementationFirst(mToolWindow, entity);
			//		else if(entity.getTreeNodeType()==TreeNodeTypeEnum.REMOVED) new ABMToolWindowNotImplementedFirst(mToolWindow, entity);
		else if(entity.getTreeNodeType()==TreeNodeTypeEnum.HIDDEN)
		{
			JOptionPane pane = new JOptionPane(mMessages.getString("main_dialog_message_remove_hidden"));
			Object[] options = new String[]{mMessages.getString("global_yes"), mMessages.getString("global_no")};
			pane.setOptions(options);
			JDialog dialog = pane.createDialog(new JFrame(), mMessages.getString("main_dialog_message_remove_hidden_header"));
			dialog.setVisible(true);
			Object obj = pane.getValue();
			int result = -1;
			for(int k = 0; k<options.length; k++)
				if(options[k].equals(obj)) result = k;
			if(result==0)
			{
				entity.setHidden(false);
				ConfigPreferences configPreferences = new ConfigPreferences();
				configPreferences.saveTreeNodeEntity(entity);
				new ABMToolWindowMain(mToolWindow);
			}
		}
	}


	private List<TreeNodeEntity> analyzeBlueprint(ABMEntity object)
	{
		if(object==null || object.getError()!=null) return null;

		ConfigPreferences configPreferences = new ConfigPreferences();
		List<TreeNodeEntity> outputList = new ArrayList<TreeNodeEntity>();
		for(ResourceGroupsEntity resourceGroupsEntity : object.getAst().getResourceGroups())
		{
			for(ResourcesEntity resourcesEntity : resourceGroupsEntity.getResources())
			{
				for(ActionsEntity actionsEntity : resourcesEntity.getActions())
				{
					TreeNodeEntity entity = new TreeNodeEntity();
					entity.setUri(resourcesEntity.getUriTemplate());                        // URI
					entity.setMethod(actionsEntity.getMethod());                            // METHOD
					entity.setMethodName("");                                               // METHOD NAME

					List<ParametersEntity> parametersList = new ArrayList<ParametersEntity>();
					// Check parameters
					for(ParametersEntity entityParameter : actionsEntity.getParameters())
					{
						if(entity.getUri().matches("(?i)^.*[{, ]" + entityParameter.getName() + "[ ,}].*$"))
						{
							entityParameter.setTypeOfParam("Path");
							parametersList.add(entityParameter);
						}
						else if(entity.getUri().matches("(?i)^.*[{, ][?]" + entityParameter.getName() + "[ ,}].*$"))
						{
							entityParameter.setTypeOfParam("Query");
							parametersList.add(entityParameter);
						}
					}
					entity.setParameters(parametersList);                                   // PARAMETERS

					if(actionsEntity.getExamples()!=null && actionsEntity.getExamples().size()>0)
					{
						ExamplesEntity examplesEntity = actionsEntity.getExamples().get(0);

						if(examplesEntity.getRequests()!=null && examplesEntity.getRequests().size()>0)
						{
							RequestEntity requestEntity = examplesEntity.getRequests().get(0);
							entity.setRequestHeaders(requestEntity.getHeaders());           // REQUEST HEADERS
							entity.setRequestBodyJson(requestEntity.getBody());             // REQUEST BODY JSON
							if(examplesEntity.getRequests().size()>1)
							{
								TreeNodeEntity entityError = new TreeNodeEntity();
								entityError.setTreeNodeType(TreeNodeTypeEnum.BLUEPRINT_PROBLEM);
								entityError.setText("Method: " + entity.getMethod() + "   URI: " + entity.getUri() + " - multiple requests!");
								outputList.add(entityError);
							}
						}
						else
						{
							entity.setRequestHeaders(null);
							entity.setRequestBodyJson("");
							entity.setRequestBody(null);
						}

						if(examplesEntity.getResponses()!=null && examplesEntity.getResponses().size()>0)
						{
							ResponsesEntity responsesEntity = examplesEntity.getResponses().get(0);
							entity.setResponseCode(responsesEntity.getName());              // RESPONSE CODE
							entity.setResponseHeaders(responsesEntity.getHeaders());        // RESPONSE HEADERS
							entity.setResponseBodyJson(responsesEntity.getBody());          // RESPONSE BODY JSON
							if(examplesEntity.getRequests().size()>1)
							{
								TreeNodeEntity entityError = new TreeNodeEntity();
								entityError.setTreeNodeType(TreeNodeTypeEnum.BLUEPRINT_PROBLEM);
								entityError.setText("Method: " + entity.getMethod() + "   URI: " + entity.getUri() + " - multiple responses!");
								outputList.add(entityError);
							}
						}
						else
						{
							entity.setResponseCode("");
							entity.setResponseHeaders(null);
							entity.setResponseBodyJson("");
							entity.setResponseBody(null);
						}

						outputList.add(entity);
					}
					else
					{
						TreeNodeEntity entityError = new TreeNodeEntity();
						entityError.setTreeNodeType(TreeNodeTypeEnum.BLUEPRINT_PROBLEM);
						entityError.setText("Method: " + entity.getMethod() + "   URI: " + entity.getUri() + " - no example!");
						outputList.add(entityError);
					}
				}
			}
		}

		for(TreeNodeEntity item : outputList)
		{
			List<BodyObjectEntity> requests = parseBodyJson(item.getRequestBodyJson());
			List<BodyObjectEntity> responses = parseBodyJson(item.getResponseBodyJson());

			// remove duplicates
			//			if(requests!=null)
			//			{
			//				Set<BodyObjectEntity> deDupleRequests = new LinkedHashSet<BodyObjectEntity>(requests);
			//				requests.clear();
			//				requests.addAll(deDupleRequests);
			//			}
			//			if(responses!=null)
			//			{
			//				Set<BodyObjectEntity> deDupleResponses = new LinkedHashSet<BodyObjectEntity>(responses);
			//				responses.clear();
			//				responses.addAll(deDupleResponses);
			//			}

			// todo join requests of same name
			if(requests!=null)
			{
				for(BodyObjectEntity entity1 : requests)
				{
					List<BodyVariableEntity> varList = new ArrayList<BodyVariableEntity>();
					varList.addAll(entity1.getVariables());
					for(BodyObjectEntity entity2 : requests)
					{
						if(entity1.getSerializableName().equals(entity2.getSerializableName()))
							for(BodyVariableEntity varEntity : entity2.getVariables())
								if(!BodyVariableEntity.existInVariableList(varList, varEntity)) varList.add(varEntity);
					}
					entity1.setVariables(varList);
				}
			}
			if(responses!=null)
			{
				for(BodyObjectEntity entity1 : responses)
				{
					List<BodyVariableEntity> varList = new ArrayList<BodyVariableEntity>();
					varList.addAll(entity1.getVariables());
					for(BodyObjectEntity entity2 : responses)
					{
						if(entity1.getSerializableName().equals(entity2.getSerializableName()))
							for(BodyVariableEntity varEntity : entity2.getVariables())
								if(!BodyVariableEntity.existInVariableList(varList, varEntity)) varList.add(varEntity);
					}
					entity1.setVariables(varList);
				}
			}


			// remove duplicates
			if(requests!=null)
			{
				Set<BodyObjectEntity> deDupleRequests = new LinkedHashSet<BodyObjectEntity>(requests);
				requests.clear();
				requests.addAll(deDupleRequests);
			}
			if(responses!=null)
			{
				Set<BodyObjectEntity> deDupleResponses = new LinkedHashSet<BodyObjectEntity>(responses);
				responses.clear();
				responses.addAll(deDupleResponses);
			}


			item.setRequestBody(requests);
			item.setResponseBody(responses);

			// complete information from config.
			configPreferences.tryToFillTreeNodeEntity(item);

			// Fill type names
			if(requests!=null)
			{
				for(BodyObjectEntity ent : requests)
				{
					for(BodyVariableEntity entVar : ent.getVariables())
						if(entVar.getType()==VariableEnum.STRING) entVar.setTypeName("String");
						else if(entVar.getType()==VariableEnum.INTEGER) entVar.setTypeName("Integer");
						else if(entVar.getType()==VariableEnum.DOUBLE) entVar.setTypeName("Double");
						else if(entVar.getType()==VariableEnum.BOOLEAN) entVar.setTypeName("Boolean");
						else if(entVar.getType()==VariableEnum.ENUM) entVar.setTypeName("ENUM");
						else if(entVar.getType()==VariableEnum.COLLECTION)
						{
							if(Utils.findEntityNameInBodyObjectList(responses, entVar.getName())!=null)
								entVar.setTypeName("List<" + Utils.findEntityNameInBodyObjectList(responses, entVar.getName()) + ">");
							else if(!entVar.getTypeName().isEmpty()) entVar.setTypeName("List<" + entVar.getTypeName() + ">");
							else entVar.setTypeName("");
						}
						else if(entVar.getType()==VariableEnum.MAP)
							entVar.setTypeName(Utils.findEntityNameInBodyObjectList(requests, entVar.getName()));
						else if(entVar.getType()==VariableEnum.NONE) entVar.setTypeName("NONE");
						else entVar.setTypeName("ERROR");
				}
			}
			if(responses!=null)
			{
				for(BodyObjectEntity ent : responses)
				{
					for(BodyVariableEntity entVar : ent.getVariables())
						if(entVar.getType()==VariableEnum.STRING) entVar.setTypeName("String");
						else if(entVar.getType()==VariableEnum.INTEGER) entVar.setTypeName("Integer");
						else if(entVar.getType()==VariableEnum.DOUBLE) entVar.setTypeName("Double");
						else if(entVar.getType()==VariableEnum.BOOLEAN) entVar.setTypeName("Boolean");
						else if(entVar.getType()==VariableEnum.ENUM) entVar.setTypeName("ENUM");
						else if(entVar.getType()==VariableEnum.COLLECTION)
						{
							if(Utils.findEntityNameInBodyObjectList(responses, entVar.getName())!=null)
								entVar.setTypeName("List<" + Utils.findEntityNameInBodyObjectList(responses, entVar.getName()) + ">");
							else if(!entVar.getTypeName().isEmpty()) entVar.setTypeName("List<" + entVar.getTypeName() + ">");
							else entVar.setTypeName("");
						}
						else if(entVar.getType()==VariableEnum.MAP)
							entVar.setTypeName(Utils.findEntityNameInBodyObjectList(responses, entVar.getName()));
						else if(entVar.getType()==VariableEnum.NONE) entVar.setTypeName("NONE");
						else entVar.setTypeName("ERROR");
				}
			}

			//			if(requests!=null)
			//			{
			//				for(BodyObjectEntity ent : requests)
			//				{
			//					for(BodyVariableEntity entVar : ent.getVariables())
			//						Log.d("ent: " + ent.getSerializableName() + "\tVar: " + entVar.getName() + "\tType: " + entVar.getTypeName());
			//				}
			//			}
			//			if(responses!=null)
			//			{
			//				for(BodyObjectEntity ent : responses)
			//				{
			//					for(BodyVariableEntity entVar : ent.getVariables())
			//						Log.d("ent: " + ent.getSerializableName() + "\tVar: " + entVar.getName() + "\tType: " + entVar.getTypeName());
			//				}
			//			}
		}

		return outputList;
	}


	private List<BodyObjectEntity> parseBodyJson(String json)
	{
		if(json==null || json.equals("")) return null;

		List<BodyObjectEntity> list = new ArrayList<BodyObjectEntity>();

		try
		{
			Object object = new Gson().fromJson(json, Object.class);

			parseBodyJsonValues(list, object, "");

			for(BodyObjectEntity ent : list)
			{
				if(ent.getSerializableName().equals("")) ent.setSerializableName("ROOT");
				for(BodyVariableEntity entVar : ent.getVariables())
					if(entVar.getType() instanceof String) entVar.setType(VariableEnum.STRING);
					else if(entVar.getType() instanceof Number)
						if(((Number) entVar.getType()).intValue()==((Number) entVar.getType()).doubleValue())
							entVar.setType(VariableEnum.INTEGER);
						else entVar.setType(VariableEnum.DOUBLE);
					else if(entVar.getType() instanceof Boolean) entVar.setType(VariableEnum.BOOLEAN);
					else if(entVar.getType() instanceof Enum) entVar.setType(VariableEnum.ENUM);
					else if(entVar.getType() instanceof Collection) entVar.setType(VariableEnum.COLLECTION); // List
					else if(entVar.getType() instanceof Map) entVar.setType(VariableEnum.MAP); // Another object
					else entVar.setType(VariableEnum.NONE);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}

		return list;
	}


	private String parseBodyJsonValues(List<BodyObjectEntity> list, Object object, String rootName)
	{
		if(object instanceof Map)
		{
			Map map = (Map) object;

			List<BodyVariableEntity> variablesList = new ArrayList<BodyVariableEntity>();
			for(Object o : map.entrySet())
			{
				Map.Entry thisEntry = (Map.Entry) o;
				String var = parseBodyJsonValues(list, thisEntry.getValue(), thisEntry.getKey().toString());
				variablesList.add(new BodyVariableEntity(thisEntry.getKey().toString(), thisEntry.getValue(), var));
			}

			BodyObjectEntity entity = new BodyObjectEntity(rootName, "", variablesList);
			list.add(0, entity);

			return "";
		}
		else if(object instanceof Collection)
		{
			String var = "";
			for(Object value : (Collection) object)
			{
				var = parseBodyJsonValues(list, value, rootName);
			}
			return var;
		}
		else
		{
			if(object instanceof String) return "String";
			else if(object instanceof Number) if(((Number) object).intValue()==((Number) object).doubleValue()) return "Integer";
			else return "Double";
			else if(object instanceof Boolean) return "Boolean";
			else if(object instanceof Enum) return "ENUM";
			else return "";
		}
	}


	private List<TreeNodeEntity> analyzeTreeNodeList(List<TreeNodeEntity> inputTreeNodeList)
	{
		PsiClass interfaceClass = ProjectManager.getInterfaceClass();
		PsiClass managerClass = ProjectManager.getManagerClass();
		List<TreeNodeEntity> outputTreeNodeList = new ArrayList<TreeNodeEntity>(inputTreeNodeList);

		if(!(interfaceClass!=null && managerClass!=null))
		{
			outputTreeNodeList.clear();

			if(interfaceClass==null)
			{
				TreeNodeEntity entity = new TreeNodeEntity();
				entity.setText("Interface class is not configured properly");
				entity.setTreeNodeType(TreeNodeTypeEnum.CONFIGURATION_PROBLEM);
				outputTreeNodeList.add(entity);
			}

			if(managerClass==null)
			{
				TreeNodeEntity entity = new TreeNodeEntity();
				entity.setText("Manager class is not configured properly");
				entity.setTreeNodeType(TreeNodeTypeEnum.CONFIGURATION_PROBLEM);
				outputTreeNodeList.add(entity);
			}
			return outputTreeNodeList;
		}

		// TODO check status of each entity
		for(TreeNodeEntity entity : inputTreeNodeList)
		{
			if(entity.getTreeNodeType()!=null) continue;

			if(entity.isHidden()) entity.setTreeNodeType(TreeNodeTypeEnum.HIDDEN);
			else
			{
				if(entity.getMethodName().equals("") || interfaceClass.findMethodsByName(entity.getMethodName(), true)==null)
					entity.setTreeNodeType(TreeNodeTypeEnum.NOT_IMPLEMENTED);
				else
				{
					boolean ok = true;

					// check if method is ok
					List<ProblemEntity> methodProblems = ProjectManager.checkMethodForProblems(entity);

					if(!methodProblems.isEmpty())
					{
						ok = false;
						Log.d("BEGIN");
						for(ProblemEntity problemEntity : methodProblems)
							Log.d("Problem: " + problemEntity.getName() + "\tText: " + problemEntity.getText());

						Log.d("END");
					}

					// check if all entities are ok
					if(entity.getRequestBody()!=null) for(BodyObjectEntity bodyEntity : entity.getRequestBody())
					{
						List<ProblemEntity> entityProblems = ProjectManager.checkEntityForProblems(bodyEntity);

						if(!entityProblems.isEmpty())
						{
							ok = false;
							Log.d("BEGIN");
							for(ProblemEntity problemEntity : entityProblems)
								Log.d("Problem: " + problemEntity.getName() + "\tText: " + problemEntity.getText());

							Log.d("END");
						}
					}

					if(entity.getResponseBody()!=null) for(BodyObjectEntity bodyEntity : entity.getResponseBody())
					{
						List<ProblemEntity> entityProblems = ProjectManager.checkEntityForProblems(bodyEntity);

						if(!entityProblems.isEmpty())
						{
							ok = false;
							Log.d("BEGIN");
							for(ProblemEntity problemEntity : entityProblems)
								Log.d("Problem: " + problemEntity.getName() + "\tText: " + problemEntity.getText());

							Log.d("END");
						}
					}

					if(ok)
						//						entity.setTreeNodeType(TreeNodeTypeEnum.MODIFIED);
						outputTreeNodeList.remove(entity);
					else entity.setTreeNodeType(TreeNodeTypeEnum.MODIFIED);
				}
			}
		}

		Iterator<TreeNodeEntity> iterator = outputTreeNodeList.iterator();
		while(iterator.hasNext())
		{
			TreeNodeEntity entity = iterator.next();
			if(entity.getTreeNodeType()==null) iterator.remove();
		}

		return outputTreeNodeList;
	}


	private DefaultMutableTreeNode initTreeStructure(List<TreeNodeEntity> nodeList)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new TreeNodeEntity(TreeNodeTypeEnum.ROOT, "Root object"));

		DefaultMutableTreeNode categoryConfiguration = new DefaultMutableTreeNode(new TreeNodeEntity(TreeNodeTypeEnum.CONFIGURATION_PROBLEM_ROOT, "Configuration problem"));
		DefaultMutableTreeNode categoryError = new DefaultMutableTreeNode(new TreeNodeEntity(TreeNodeTypeEnum.BLUEPRINT_PROBLEM_ROOT, "Blueprint problem"));
		DefaultMutableTreeNode categoryNotImplemented = new DefaultMutableTreeNode(new TreeNodeEntity(TreeNodeTypeEnum.NOT_IMPLEMENTED_ROOT, "Not implemented"));
		DefaultMutableTreeNode categoryModified = new DefaultMutableTreeNode(new TreeNodeEntity(TreeNodeTypeEnum.MODIFIED_ROOT, "Modified"));
		DefaultMutableTreeNode categoryRemoved = new DefaultMutableTreeNode(new TreeNodeEntity(TreeNodeTypeEnum.REMOVED_ROOT, "Removed"));
		DefaultMutableTreeNode categoryHidden = new DefaultMutableTreeNode(new TreeNodeEntity(TreeNodeTypeEnum.HIDDEN_ROOT, "Hidden"));
		DefaultMutableTreeNode item;

		Integer categoryConfigurationValue = 0;
		Integer categoryErrorValue = 0;
		Integer categoryCannotRecognizeValue = 0;
		Integer categoryNotImplementedValue = 0;
		Integer categoryModifiedValue = 0;
		Integer categoryRemovedValue = 0;
		Integer categoryHiddenValue = 0;

		for(TreeNodeEntity entity : nodeList)
		{
			item = new DefaultMutableTreeNode(entity);
			switch(entity.getTreeNodeType())
			{
				case CONFIGURATION_PROBLEM:
					categoryConfiguration.add(item);
					categoryConfigurationValue++;
					break;
				case BLUEPRINT_PROBLEM:
					categoryError.add(item);
					categoryErrorValue++;
					break;
				case NOT_IMPLEMENTED:
					categoryNotImplemented.add(item);
					categoryNotImplementedValue++;
					break;
				case MODIFIED:
					categoryModified.add(item);
					categoryModifiedValue++;
					break;
				case REMOVED:
					categoryRemoved.add(item);
					categoryRemovedValue++;
					break;
				case HIDDEN:
					categoryHidden.add(item);
					categoryHiddenValue++;
					break;
			}
		}


		((TreeNodeEntity) categoryError.getUserObject()).setValue(categoryErrorValue);
		((TreeNodeEntity) categoryNotImplemented.getUserObject()).setValue(categoryNotImplementedValue);
		((TreeNodeEntity) categoryModified.getUserObject()).setValue(categoryModifiedValue);
		((TreeNodeEntity) categoryRemoved.getUserObject()).setValue(categoryRemovedValue);
		((TreeNodeEntity) categoryHidden.getUserObject()).setValue(categoryHiddenValue);

		if(categoryConfigurationValue!=0) root.add(categoryConfiguration);
		if(categoryErrorValue!=0) root.add(categoryError);
		if(categoryNotImplementedValue!=0) root.add(categoryNotImplemented);
		if(categoryModifiedValue!=0) root.add(categoryModified);
		if(categoryRemovedValue!=0) root.add(categoryRemoved);
		if(categoryHiddenValue!=0) root.add(categoryHidden);


		try
		{
			final BufferedImage tmpImageCross = ImageIO.read(JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/img_cross.png"));
			final BufferedImage tmpImageExclamation = ImageIO.read(JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/img_exclamation_mark.png"));
			final BufferedImage tmpImageCheck = ImageIO.read(JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/img_check.png"));
			if(categoryCannotRecognizeValue>0 || categoryConfigurationValue>0 || categoryErrorValue>0)
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						mInformationIcon.setIcon(new ImageIcon(tmpImageCross.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
					}
				});
			else if(categoryNotImplementedValue>0 || categoryModifiedValue>0) SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					mInformationIcon.setIcon(new ImageIcon(tmpImageExclamation.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
				}
			});
			else SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						mInformationIcon.setIcon(new ImageIcon(tmpImageCheck.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
					}
				});
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		return root;
	}
}
