package com.apiary.abm.ui;

import com.apiary.abm.entity.TreeNodeEntity;
import com.apiary.abm.entity.blueprint.ABMEntity;
import com.apiary.abm.entity.blueprint.ActionsEntity;
import com.apiary.abm.entity.blueprint.ResourceGroupsEntity;
import com.apiary.abm.entity.blueprint.ResourcesEntity;
import com.apiary.abm.enums.NodeTypeEnum;
import com.apiary.abm.renderer.ABMTreeCellRenderer;
import com.apiary.abm.utility.Network;
import com.apiary.abm.utility.Preferences;
import com.apiary.abm.utility.Utils;
import com.apiary.abm.view.ImageButton;
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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingConstants;
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
		topPanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(20) + " " + Utils.reDimension(20) + " " + Utils.reDimension(20) + ", flowy, fillx, filly", "[fill, grow]", "[fill]"));
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
			String blueprint = Utils.readFileAsString(Network.refreshBlueprint(), Charset.forName("UTF-8"));
			String json = Network.requestJSONFromBlueprint(blueprint);
			object = Utils.parseJsonBlueprint(json);
			if(object.getError()==null) treeNodeList = analyzeBlueprint(object);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}


		// information icon
		try
		{
			final JLabel informationIcon = new JLabel();
			BufferedImage tmpImage = ImageIO.read(JBackgroundPanel.class.getClassLoader().getResourceAsStream("drawable/img_cross.png"));

			Image image = tmpImage.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			informationIcon.setIcon(new ImageIcon(image));
			informationIcon.setOpaque(false);
			informationIcon.setHorizontalAlignment(SwingConstants.CENTER);
			topPanel.add(informationIcon);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		// tree structure
		final JBackgroundPanel middleTreePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		middleTreePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[fill, grow]", "[fill, grow]"));
		middleTreePanel.setOpaque(false);

		JTree tree = new Tree(initTreeStructure(treeNodeList));
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
			private boolean refreshing;


			public void mouseClicked(MouseEvent e)
			{
				if(refreshing) return;
				refreshing = true;

				button.setImage("drawable/animation_refresh.gif");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));

				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						ABMEntity object;
						List<TreeNodeEntity> treeNodeList = null;
						try
						{
							Preferences prefs = new Preferences();
							String blueprint = Utils.readFileAsString(Network.refreshBlueprint(), Charset.forName("UTF-8"));
							String json;
							if(blueprint.equals("")) json = prefs.getBlueprintTmpFileLocation();
							else json = Network.requestJSONFromBlueprint(blueprint);
							object = Utils.parseJsonBlueprint(json);
							if(object.getError()==null) treeNodeList = analyzeBlueprint(object);
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}

						final JTree tree = new Tree(initTreeStructure(treeNodeList));
						tree.setRootVisible(false);
						tree.setOpaque(false);
						tree.setCellRenderer(new ABMTreeCellRenderer());

						SwingUtilities.invokeLater(new Runnable()
						{
							public void run()
							{
								middleTreePanel.removeAll();
								middleTreePanel.add(tree);
								middleTreePanel.validate();
								middleTreePanel.repaint();

								button.setImage("drawable/img_button_refresh.png");
								button.setSize(Utils.reDimension(70), Utils.reDimension(70));

								refreshing = false;
							}
						});
					}
				});
				t.start();
			}


			public void mousePressed(MouseEvent e)
			{
				if(refreshing) return;
				button.setImage("drawable/img_button_refresh_pressed.png");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}


			public void mouseReleased(MouseEvent e)
			{
				if(refreshing) return;
				button.setImage("drawable/img_button_refresh.png");
				button.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}
		});
		bottomPanel.add(button);

		// todo remove
		//		List<PsiPackage> packList = getPackages();
		//		for(PsiPackage pack : packList)
		//		{
		//			Log.d("Package: " + pack.getSubPackages()[0].getSubPackages()[0].getName());
		//		}

		//		ConfigPreferences confPrefs = new ConfigPreferences();
		//		Log.d("exist: " + ConfigPreferences.configExist());
		//		Utils.isGradleWithRetrofit();
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
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new TreeNodeEntity(NodeTypeEnum.ROOT, "Root object", ""));

		DefaultMutableTreeNode categoryError;
		DefaultMutableTreeNode categoryWarning;
		DefaultMutableTreeNode categoryMissing;
		DefaultMutableTreeNode item;

		categoryError = new DefaultMutableTreeNode(new TreeNodeEntity(NodeTypeEnum.ERROR_ROOT, "Errors", "3"));
		root.add(categoryError);

		categoryWarning = new DefaultMutableTreeNode(new TreeNodeEntity(NodeTypeEnum.WARNING_ROOT, "Warnings", "2"));
		root.add(categoryWarning);

		categoryMissing = new DefaultMutableTreeNode(new TreeNodeEntity(NodeTypeEnum.MISSING_ROOT, "Not implemented", "2"));
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


	// CODE ANALYZE
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
