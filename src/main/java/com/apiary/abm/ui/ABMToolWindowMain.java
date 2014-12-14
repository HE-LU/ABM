package com.apiary.abm.ui;

import com.apiary.abm.entity.ABMEntity;
import com.apiary.abm.entity.ActionsEntity;
import com.apiary.abm.entity.NodeTypeEnum;
import com.apiary.abm.entity.ResourceGroupsEntity;
import com.apiary.abm.entity.ResourcesEntity;
import com.apiary.abm.entity.TreeNodeEntity;
import com.apiary.abm.renderer.ABMTreeCellRenderer;
import com.apiary.abm.utility.Utils;
import com.apiary.abm.view.JBackgroundPanel;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.ide.projectView.impl.nodes.ProjectViewDirectoryHelper;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiPackage;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.containers.ContainerUtil;

import net.miginfocom.swing.MigLayout;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;


public class ABMToolWindowMain extends JFrame
{
	private ToolWindow mToolWindow;


	public ABMToolWindowMain(ToolWindow toolWindow)
	{
		mToolWindow = toolWindow;
		mToolWindow.getContentManager().removeAllContents(true);

		initLayout();
	}


	private void initLayout()
	{
		mToolWindow.getContentManager().removeAllContents(true);

		// create UI
		JBackgroundPanel myToolWindowContent = new JBackgroundPanel("img_background.png", JBackgroundPanel.JBackgroundPanelType.BACKGROUND_REPEAT);
		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		Content content = contentFactory.createContent(myToolWindowContent, "", false);
		mToolWindow.getContentManager().addContent(content);

		// MIGLAYOUT ( params, columns, rows)
		// insets TOP LEFT BOTTOM RIGHT
		myToolWindowContent.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow, center]", "[fill,top][fill][fill,bottom]"));

		JBackgroundPanel topPanel = new JBackgroundPanel("img_box_top.png", JBackgroundPanel.JBackgroundPanelType.PANEL);
		//		JBackgroundPanel middlePanel = new JBackgroundPanel("panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		JPanel middlePanel = new JPanel();
		JBackgroundPanel bottomPanel = new JBackgroundPanel("img_box_bottom.png", JBackgroundPanel.JBackgroundPanelType.PANEL);

		topPanel.setMinimumSize(new Dimension(0, Utils.reDimension(20)));
		middlePanel.setMinimumSize(new Dimension(0, Utils.reDimension(20)));
		bottomPanel.setMinimumSize(new Dimension(0, Utils.reDimension(20)));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(20)));
		middlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(20)));

		// add elements
		topPanel.setLayout(new MigLayout("insets 5 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));
		middlePanel.setLayout(new MigLayout("insets 0 20 0 20, flowy, fillx, filly", "[fill, grow]", "[fill]"));
		bottomPanel.setLayout(new MigLayout("insets 30 0 0 0, flowy, fillx, filly", "[grow, center]", "[center, top]"));

		topPanel.setOpaque(false);
		middlePanel.setOpaque(false);
		bottomPanel.setOpaque(false);

		myToolWindowContent.add(topPanel);
		myToolWindowContent.add(middlePanel);
		myToolWindowContent.add(bottomPanel);

		// information icon
		try
		{
			final JLabel informationIcon = new JLabel();
			BufferedImage tmpImage = ImageIO.read(JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/img_cross.png"));

			Image image = tmpImage.getScaledInstance(95, 95, Image.SCALE_SMOOTH);
			informationIcon.setIcon(new ImageIcon(image));
			informationIcon.setOpaque(false);
			topPanel.add(informationIcon);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}


		// refresh and analyze blueprint
		ABMEntity object = refreshBlueprint();
		//		analyzeBlueprint(object);


		// tree structure
		final JBackgroundPanel middleTreePanel = new JBackgroundPanel("img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		middleTreePanel.setLayout(new MigLayout("insets 12 12 18 19, flowy, fillx, filly", "[fill, grow]", "[fill]"));
		middleTreePanel.setOpaque(false);


		//		Tree tree = new Tree(initExampleTreeStructure(object));
		Tree tree = new Tree(initTreeStructure(analyzeBlueprint(object)));
		tree.setRootVisible(false);
		tree.setOpaque(false);
		tree.setCellRenderer(new ABMTreeCellRenderer());

		JBScrollPane pane = new JBScrollPane(tree);
		pane.setBorder(null);
		pane.setOpaque(false);

		pane.getViewport().setBorder(null);
		pane.getViewport().setOpaque(false);

		middleTreePanel.add(pane);

		middlePanel.add(middleTreePanel);

		// Button refresh
		final JLabel buttonRefresh = new JLabel();
		buttonRefresh.setOpaque(false);
		buttonRefresh.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_reload.png") + "' width='90' height='90' /></html>");

		buttonRefresh.addMouseListener(new MouseAdapter()
		{
			private boolean reloading;


			public void mouseClicked(MouseEvent e)
			{
				if(reloading) return;

				buttonRefresh.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/animation_reload.gif") + "' width='90' height='90' /></html>");
				reloading = true;

				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						ABMEntity object = refreshBlueprint();

						analyzeBlueprint(object);

						Tree tree = new Tree(initExampleTreeStructure(object));
						tree.setRootVisible(false);
						tree.setOpaque(false);
						tree.setCellRenderer(new ABMTreeCellRenderer());

						final JBScrollPane pane = new JBScrollPane(tree);
						pane.setBorder(null);
						pane.setOpaque(false);

						pane.getViewport().setBorder(null);
						pane.getViewport().setOpaque(false);

						reloading = false;

						SwingUtilities.invokeLater(new Runnable()
						{
							public void run()
							{
								middleTreePanel.removeAll();
								middleTreePanel.add(pane);

								buttonRefresh.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_reload.png") + "' width='90' height='90' /></html>");
							}
						});
					}
				});
				t.start();
			}


			public void mousePressed(MouseEvent e)
			{
				if(reloading) return;

				buttonRefresh.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_reload_pressed.png") + "' width='90' height='90' /></html>");
			}


			public void mouseReleased(MouseEvent e)
			{
				if(reloading) return;

				buttonRefresh.setText("<html><img src='" + JBackgroundPanel.class.getClassLoader().getResource("drawable/img_button_reload.png") + "' width='90' height='90' /></html>");
			}

		});
		bottomPanel.add(buttonRefresh);
	}


	private List<TreeNodeEntity> analyzeBlueprint(ABMEntity object)
	{
		if(object==null || object.getError()!=null) return null;

		List<TreeNodeEntity> outputList = new ArrayList<TreeNodeEntity>();
		for(ResourceGroupsEntity resourceGroupsEntity : object.getAst().getResourceGroups())
		{
			for(ResourcesEntity resourcesEntity : resourceGroupsEntity.getResources())
			{
				for(ActionsEntity actionsEntity : resourcesEntity.getActions())
				{
					TreeNodeEntity entity = new TreeNodeEntity();
					entity.setName(actionsEntity.getName().replace(" ", ""));
					entity.setUri(resourcesEntity.getUriTemplate());
					entity.setMethod(actionsEntity.getMethod());

					if(entity.getMethod().equals("GET")) entity.setNodeType(NodeTypeEnum.ERROR);
					else if(entity.getMethod().equals("POST")) entity.setNodeType(NodeTypeEnum.WARNING);
					else entity.setNodeType(NodeTypeEnum.MISSING);

					outputList.add(entity);
				}
			}
		}
		return outputList;
	}


	private DefaultMutableTreeNode initTreeStructure(List<TreeNodeEntity> nodeList)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ROOT, "Root object", ""));

		DefaultMutableTreeNode categoryError = null;
		DefaultMutableTreeNode categoryWarning = null;
		DefaultMutableTreeNode categoryMissing = null;
		DefaultMutableTreeNode item = null;

		categoryError = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ERROR_ROOT, "Errors", "3"));
		root.add(categoryError);

		categoryWarning = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.WARNING_ROOT, "Warnings", "2"));
		root.add(categoryWarning);

		categoryMissing = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.MISSING_ROOT, "Not implemented", "2"));
		root.add(categoryMissing);

		for(TreeNodeEntity entity : nodeList)
		{
			if(entity.getMethod().equals("GET"))
			{
				item = new DefaultMutableTreeNode(entity);
				categoryError.add(item);
			}
			else if(entity.getMethod().equals("POST"))
			{
				item = new DefaultMutableTreeNode(entity);
				categoryWarning.add(item);
			}
			else
			{
				item = new DefaultMutableTreeNode(entity);
				categoryMissing.add(item);
			}

		}

		return root;
	}


	// Utility
	private ABMEntity refreshBlueprint()
	{
		ABMEntity object = null;
		try
		{
			com.apiary.abm.utility.Preferences preferences = new com.apiary.abm.utility.Preferences();
			String inputFilePath = Utils.saveWebFileToTmp(preferences.getApiaryBlueprintUrl());
			String tmp = Utils.readFileAsString(inputFilePath, StandardCharsets.UTF_8);

			// parse json from raw blueprint
			String tmp_string = Utils.parseJsonFromBlueprint(tmp);

			// convert json string into object
			object = Utils.getJsonObject(tmp_string);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return object;
	}


	private TreeNodeEntity createTreeNodeItem(NodeTypeEnum type, String name, String value)
	{
		return new TreeNodeEntity(type, name, value);
	}


	// Not used
	private DefaultMutableTreeNode initExampleTreeStructure(ABMEntity object)
	{
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ROOT, "Root", "1"));

		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode book = null;

		category = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ERROR_ROOT, "Errors", "3"));
		top.add(category);

		//original Tutorial
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ERROR, "File: test.java", "Missing argument test"));
		category.add(book);

		//Tutorial Continued
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ERROR, "File: date.java", "Missing argument day"));
		category.add(book);

		//Swing Tutorial
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.ERROR, "File: date.java", "Missing argument month"));
		category.add(book);

		//...add more books for programmers...

		category = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.WARNING_ROOT, "Warnings", "2"));
		top.add(category);

		//VM
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.WARNING, "File: test.java", "Bad argument type String"));
		category.add(book);

		//Language Spec
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.WARNING, "File: date.java", "Bad argument type Date"));
		category.add(book);

		category = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.MISSING_ROOT, "Not implemented", "2"));
		top.add(category);

		//VM
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.MISSING, "File: test.java", "Missing argument test2"));
		category.add(book);

		//Language Spec
		book = new DefaultMutableTreeNode(createTreeNodeItem(NodeTypeEnum.MISSING, "File: date.java", "Missing argument year"));
		category.add(book);

		return top;
	}


	public class ProjectViewSettings implements ViewSettings
	{
		@Override
		public boolean isShowMembers()
		{
			return false;
		}


		@Override
		public boolean isStructureView()
		{
			return false;
		}


		@Override
		public boolean isShowModules()
		{
			return false;
		}


		@Override
		public boolean isFlattenPackages()
		{
			return false;
		}


		@Override
		public boolean isAbbreviatePackageNames()
		{
			return false;
		}


		@Override
		public boolean isHideEmptyMiddlePackages()
		{
			return false;
		}


		@Override
		public boolean isShowLibraryContents()
		{
			return false;
		}
	}


	private List<PsiPackage> getPackages()
	{
		//		Project myProject = presenterConfigModel.getProject();
		Project myProject = ABMToolWindow.getProject();

		ProjectViewSettings viewSettings = new ProjectViewSettings();

		final List<VirtualFile> sourceRoots = new ArrayList<VirtualFile>();
		final ProjectRootManager projectRootManager = ProjectRootManager.getInstance(myProject);
		ContainerUtil.addAll(sourceRoots, projectRootManager.getContentSourceRoots());

		final PsiManager psiManager = PsiManager.getInstance(myProject);
		final List<AbstractTreeNode> children = new ArrayList<AbstractTreeNode>();
		final Set<PsiPackage> topLevelPackages = new HashSet<PsiPackage>();

		for(final VirtualFile root : sourceRoots)
		{
			final PsiDirectory directory = psiManager.findDirectory(root);
			if(directory==null)
			{
				continue;
			}
			final PsiPackage directoryPackage = JavaDirectoryService.getInstance().getPackage(directory);
			if(directoryPackage==null || PackageUtil.isPackageDefault(directoryPackage))
			{
				// add subpackages
				final PsiDirectory[] subdirectories = directory.getSubdirectories();
				for(PsiDirectory subdirectory : subdirectories)
				{
					final PsiPackage aPackage = JavaDirectoryService.getInstance().getPackage(subdirectory);
					if(aPackage!=null && !PackageUtil.isPackageDefault(aPackage))
					{
						topLevelPackages.add(aPackage);
					}
				}
				// add non-dir items
				children.addAll(ProjectViewDirectoryHelper.getInstance(myProject).getDirectoryChildren(directory, viewSettings, false));
			}
			else
			{
				// this is the case when a source root has package prefix assigned
				topLevelPackages.add(directoryPackage);
			}
		}

		return new ArrayList<PsiPackage>(topLevelPackages);
	}


}
