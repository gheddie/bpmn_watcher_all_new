package de.gravitex.bpmn.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import de.gravitex.bpmn.client.singleton.ProcessingSingleton;
import de.gravitex.bpmn.client.util.TableColumnConfigFactory;
import de.gravitex.bpmn.client.util.table.GenericBpmnTable;
import de.gravitex.bpmn.server.dto.VariableInstanceDTO;
import de.gravitex.bpmn.server.dto.VariableState;
import de.gravitex.bpmn.server.exception.BpmnException;
import de.gravitex.bpmn.server.exception.BpmnPropertyException;

public class EnhandedProcessFrame extends JFrame
{

	private static Logger logger = Logger.getLogger(EnhandedProcessFrame.class);

	private static final long serialVersionUID = 1L;

	private ProcessDefinition selectedProcessDefinition;

	private ProcessInstance selectedProcessInstance;

	private Task actualTask;

	private HashMap<String, Object> stagedProcessVariables;

	private VariableInstanceDTO selectedProcessVariable;

	private UpdateProcessThread updateThread;

	private static final int TAB_INDEX_OVERVIEW = 0;

	private static final int TAB_INDEX_DEFINITIONS = 1;

	private void startUpdateThread()
	{
		this.updateThread = new UpdateProcessThread(this);
		// this.updateThread.start();
	}

	public EnhandedProcessFrame()
	{

		super("EnhandedProcessFrame");

		initComponents();
		putListeners();
		setSize(1200, 900);

		prepareTables();

		setVisible(true);

		initProcessVariables();

		fillDeployedDefinitions();
		fillRunningInstances();

		startUpdateThread();
	}

	private void initProcessVariables()
	{
		stagedProcessVariables = new HashMap<>();
	}

	private void prepareTables()
	{

		// tbDeployedProcessDefinitions
		tbDeployedProcessDefinitions.setRelatedMethod("deployedDefinitions");
		tbDeployedProcessDefinitions.setRelatedView(this);

		// tbRunningInstances
		tbRunningInstances.setRelatedMethod("runningInstances");
		tbRunningInstances.setRelatedView(this);

		// tbActualTasks
		tbActualTasks.setRelatedMethod("actualTasks");
		tbActualTasks.setRelatedView(this);
		tbActualTasks.addMouseListener(new MouseListener()
		{
			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub
			}

			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub
			}

			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
			}

			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
			}

			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					System.out.println("mouse double clicked...");
					try
					{
						completeTask(null);
					} catch (BpmnException e1)
					{
						if (e1 instanceof BpmnPropertyException)
						{
							handleSimpleExecution((BpmnPropertyException) e1);	
						}
					}
				}
			}
		});

		// tbVariables
		tbVariables.setRelatedMethod("processVariables");
		tbVariables.setRelatedView(this);
	}

	private void fillDeployedDefinitions()
	{
		List<ProcessDefinition> definitions;
		try
		{
			definitions = ProcessingSingleton.getInstance().queryDefinitions();
			tbDeployedProcessDefinitions.setData(definitions,
					TableColumnConfigFactory.getDeployedDefinitionsColumn());
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	private void fillRunningInstances()
	{
		List<ProcessInstance> runningInstances;
		try
		{
			runningInstances = ProcessingSingleton.getInstance()
					.queryInstances();
			tbRunningInstances.setData(runningInstances,
					TableColumnConfigFactory.getRunningInstancesColumnConfig());
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	private void putListeners()
	{
		btnRemoveVariable.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				deleteSelectedVariable();
			}
		});
		// ---
		btnRefreshVariables.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fillProcessVariables();
			}
		});
		// ---
		btnAddVariable.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				Point location = getLocation();
				VariablesDialog dialog = new VariablesDialog(
						EnhandedProcessFrame.this,
						((int) location.getX()) + 100,
						((int) location.getY()) + 100);
				VariableInstanceDTO stagedVariable = dialog.getVariable();
				System.out
						.println("variable created : " + stagedVariable + ".");
				stagedProcessVariables.put(stagedVariable.getName(),
						stagedVariable.getValue());
				fillProcessVariables();
			}
		});
		// ---
		btnSizeUp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				bpmnPanel.increaseZoom();
			}
		});
		// ---
		btnSizeDown.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				bpmnPanel.decreaseZoom();
			}
		});
		// ---
		btnResetSize.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				bpmnPanel.resetZoom();
			}
		});
		// ---
		btnStartInstance.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				startProcessInstance();
				fillRunningInstances();
			}
		});
		// ---
		btnCompleteTask.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					completeTask(null);
				} catch (BpmnException e1)
				{
					if (e1 instanceof BpmnPropertyException)
					{
						handleSimpleExecution((BpmnPropertyException) e1);	
					}
				}
			}
		});
		// ---
		btnRefreshProcess.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				refreshProcessPanel();
			}
		});
		// ---
		btnRefreshInstances.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				refreshInstances();
			}
		});
		// ---
		btnRefreshDefinitions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fillDeployedDefinitions();
			}
		});
	}

	private void handleSimpleExecution(BpmnPropertyException exception)
	{
		int result = JOptionPane.showConfirmDialog((Component) null,
				"Resolve '"+exception.getPropertyName()+"' simple?", "Resolve simple?", JOptionPane.YES_NO_CANCEL_OPTION);
		boolean value = false;
		switch (result) {
		case JOptionPane.CANCEL_OPTION:
			return;
		case JOptionPane.YES_OPTION:
			value = true;
			break;
		case JOptionPane.NO_OPTION:
			value = false;
			break;
		}
		try
		{
			HashMap<String, Object> values = new HashMap<>();
			values.put(exception.getPropertyName(), value);
			completeTask(values);
		} catch (BpmnException e)
		{
			e.printStackTrace();
		}
	}

	private void deleteSelectedVariable()
	{
		if (selectedProcessVariable == null)
		{
			logger.warn("selected variable is NULL--> returning.");
			return;
		}
		if (selectedProcessVariable.isDeployed())
		{
			logger.warn("can not delete deployed process variable--> returning.");
			return;
		}
		stagedProcessVariables.remove(selectedProcessVariable);
		selectedProcessVariable = null;
		fillProcessVariables();
	}

	private void fillProcessVariables()
	{
		List<VariableInstanceDTO> variableInstances = null;
		try
		{
			// deployed variables
			variableInstances = ProcessingSingleton.getInstance()
					.queryVariables(selectedProcessInstance.getId());
			System.out.println(variableInstances.size()
					+ " variable instances found.");

			// staged variables
			for (String key : stagedProcessVariables.keySet())
			{
				variableInstances.add(new VariableInstanceDTO(key,
						stagedProcessVariables.get(key), VariableState.STAGED));
			}

			tbVariables.setData(variableInstances,
					TableColumnConfigFactory.getVaribleInstanceColumnConfig());
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	private void refreshInstances()
	{
		fillRunningInstances();
	}

	private void startProcessInstance()
	{
		if (selectedProcessDefinition == null)
		{
			logger.warn("selected process definition is NULL--> returning.");
			return;
		}
		try
		{
			ProcessingSingleton.getInstance().startProcessInstanceByKey(
					selectedProcessDefinition.getKey(), stagedProcessVariables);
			initProcessVariables();
			changeTabIndex(TAB_INDEX_OVERVIEW);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	private void completeTask(HashMap<String, Object> givenVariables) throws BpmnException
	{
		if (actualTask == null)
		{
			logger.warn("selected actual task is NULL--> returning.");
			return;
		}
		try
		{
			logger.trace("trying to complete task '" + actualTask.getName()
					+ "' [ID=" + actualTask.getId() + "]...");
			if (givenVariables == null)
			{
				ProcessingSingleton.getInstance().completeTask(actualTask.getId(),
						stagedProcessVariables);
			}
			else
			{
				ProcessingSingleton.getInstance().completeTask(actualTask.getId(),
						givenVariables);
			}
			this.actualTask = null;
			refreshProcessPanel();
			initProcessVariables();
			resetError();
		} catch (BpmnException e)
		{
			lblError.setText(e.getMessage() + " ["
					+ e.getCause().getClass().getCanonicalName() + "]");
			throw e;
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	private void resetError()
	{
		lblError.setText("");
	}

	private void changeTabIndex(int index)
	{
		tbpMain.setSelectedIndex(index);
	}

	public void refreshProcessPanel()
	{
		if (selectedProcessInstance == null)
		{
			logger.trace("NOT refreshing process panel --> no process instance selected...");
			return;
		}
		logger.trace("refreshing process panel...");
		List<Task> activeTasks = null;
		try
		{
			activeTasks = ProcessingSingleton.getInstance().queryTasks(
					selectedProcessInstance.getId());
			bpmnPanel.initProcess(selectedProcessInstance);
			logger.trace(activeTasks.size() + " tasks set...");
			tbActualTasks.setData(activeTasks,
					TableColumnConfigFactory.getActualTasksColumnConfig());
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	public void deployedDefinitionsBeanSelected(
			Object selectedProcessDefinitionObj)
	{
		this.selectedProcessDefinition = (ProcessDefinition) selectedProcessDefinitionObj;
	}

	public void runningInstancesBeanSelected(Object selectedProcessInstanceObj)
	{
		this.selectedProcessInstance = (ProcessInstance) selectedProcessInstanceObj;
		refreshProcessPanel();
		fillProcessVariables();
	}

	public void actualTasksBeanSelected(Object selectedActualTaskObj)
	{
		this.actualTask = (Task) selectedActualTaskObj;
	}

	public void processVariablesBeanSelected(Object selectedProcessVariableObj)
	{
		this.selectedProcessVariable = (VariableInstanceDTO) selectedProcessVariableObj;
	}

	private void initComponents()
	{
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		tbMain = new JToolBar();
		btnRefreshProcess = new JButton();
		btnSizeUp = new JButton();
		btnSizeDown = new JButton();
		btnResetSize = new JButton();
		tbpMain = new JTabbedPane();
		pnlOverview = new JPanel();
		bpmnView = new JPanel();
		bpmnScroller = new JScrollPane();
		bpmnPanel = new BpmnPanel();
		lblError = new JLabel();
		pnlActualTasks = new JPanel();
		scActualTasks = new JScrollPane();
		tbActualTasks = new GenericBpmnTable();
		btnCompleteTask = new JButton();
		pnlRunningInstances = new JPanel();
		scRunningInstances = new JScrollPane();
		tbRunningInstances = new GenericBpmnTable();
		btnRefreshInstances = new JButton();
		pnlVariables = new JPanel();
		scVariables = new JScrollPane();
		tbVariables = new GenericBpmnTable();
		btnRefreshVariables = new JButton();
		btnAddVariable = new JButton();
		btnRemoveVariable = new JButton();
		pnlDeployedDefinitions = new JPanel();
		scDeployedProcessDefinitions = new JScrollPane();
		tbDeployedProcessDefinitions = new GenericBpmnTable();
		btnStartInstance = new JButton();
		btnRefreshDefinitions = new JButton();

		// ======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[]
		{ 0, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[]
		{ 0, 0, 0 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[]
		{ 1.0, 1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[]
		{ 0.0, 1.0, 1.0E-4 };

		// ======== tbMain ========
		{
			tbMain.setFloatable(false);

			// ---- btnRefreshProcess ----
			btnRefreshProcess.setText("Prozess aktualisieren");
			tbMain.add(btnRefreshProcess);

			// ---- btnSizeUp ----
			btnSizeUp.setText("Vergr\u00f6ssern");
			tbMain.add(btnSizeUp);

			// ---- btnSizeDown ----
			btnSizeDown.setText("Verkleinern");
			tbMain.add(btnSizeDown);

			// ---- btnResetSize ----
			btnResetSize.setText("Normalgr\u00f6sse");
			tbMain.add(btnResetSize);
		}
		contentPane.add(tbMain, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 5, 0), 0, 0));

		// ======== tbpMain ========
		{

			// ======== pnlOverview ========
			{
				pnlOverview.setLayout(new GridBagLayout());
				((GridBagLayout) pnlOverview.getLayout()).columnWidths = new int[]
				{ 624, 91, 0, 0 };
				((GridBagLayout) pnlOverview.getLayout()).rowHeights = new int[]
				{ 297, 0, 114, 0 };
				((GridBagLayout) pnlOverview.getLayout()).columnWeights = new double[]
				{ 1.0, 0.0, 0.0, 1.0E-4 };
				((GridBagLayout) pnlOverview.getLayout()).rowWeights = new double[]
				{ 1.0, 0.0, 0.0, 1.0E-4 };

				// ======== bpmnView ========
				{
					bpmnView.setBorder(new TitledBorder(
							"Aktueller Prozess-Status"));
					bpmnView.setLayout(new GridBagLayout());
					((GridBagLayout) bpmnView.getLayout()).columnWidths = new int[]
					{ 619, 0 };
					((GridBagLayout) bpmnView.getLayout()).rowHeights = new int[]
					{ 292, 0 };
					((GridBagLayout) bpmnView.getLayout()).columnWeights = new double[]
					{ 1.0, 1.0E-4 };
					((GridBagLayout) bpmnView.getLayout()).rowWeights = new double[]
					{ 1.0, 1.0E-4 };

					// ======== bpmnScroller ========
					{
						bpmnScroller.setViewportView(bpmnPanel);
					}
					bpmnView.add(bpmnScroller, new GridBagConstraints(0, 0, 1,
							1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
							0));
				}
				pnlOverview.add(bpmnView, new GridBagConstraints(0, 0, 3, 1,
						0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0, 0));

				// ---- lblError ----
				lblError.setForeground(Color.red);
				pnlOverview.add(lblError, new GridBagConstraints(0, 1, 2, 1,
						0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

				// ======== pnlActualTasks ========
				{
					pnlActualTasks.setBorder(new TitledBorder(
							"Aktuelle Aufgaben"));
					pnlActualTasks.setLayout(new GridBagLayout());
					((GridBagLayout) pnlActualTasks.getLayout()).columnWidths = new int[]
					{ 475, 0 };
					((GridBagLayout) pnlActualTasks.getLayout()).rowHeights = new int[]
					{ 76, 0, 0 };
					((GridBagLayout) pnlActualTasks.getLayout()).columnWeights = new double[]
					{ 1.0, 1.0E-4 };
					((GridBagLayout) pnlActualTasks.getLayout()).rowWeights = new double[]
					{ 1.0, 0.0, 1.0E-4 };

					// ======== scActualTasks ========
					{
						scActualTasks.setViewportView(tbActualTasks);
					}
					pnlActualTasks.add(scActualTasks, new GridBagConstraints(0,
							0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0,
							0));

					// ---- btnCompleteTask ----
					btnCompleteTask.setText("Aufgabe vollenden");
					pnlActualTasks.add(btnCompleteTask, new GridBagConstraints(
							0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
							0));
				}
				pnlOverview.add(pnlActualTasks, new GridBagConstraints(0, 2, 1,
						1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));

				// ======== pnlRunningInstances ========
				{
					pnlRunningInstances.setBorder(new TitledBorder(
							"Laufende Instanzen"));
					pnlRunningInstances.setLayout(new GridBagLayout());
					((GridBagLayout) pnlRunningInstances.getLayout()).columnWidths = new int[]
					{ 224, 0 };
					((GridBagLayout) pnlRunningInstances.getLayout()).rowHeights = new int[]
					{ 119, 0, 0 };
					((GridBagLayout) pnlRunningInstances.getLayout()).columnWeights = new double[]
					{ 0.0, 1.0E-4 };
					((GridBagLayout) pnlRunningInstances.getLayout()).rowWeights = new double[]
					{ 0.0, 0.0, 1.0E-4 };

					// ======== scRunningInstances ========
					{
						scRunningInstances.setViewportView(tbRunningInstances);
					}
					pnlRunningInstances.add(scRunningInstances,
							new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											5, 0), 0, 0));

					// ---- btnRefreshInstances ----
					btnRefreshInstances.setText("Aktualisieren");
					pnlRunningInstances.add(btnRefreshInstances,
							new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											0, 0), 0, 0));
				}
				pnlOverview.add(pnlRunningInstances, new GridBagConstraints(1,
						2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));

				// ======== pnlVariables ========
				{
					pnlVariables
							.setBorder(new TitledBorder("Prozess-Variablen"));
					pnlVariables.setLayout(new GridBagLayout());
					((GridBagLayout) pnlVariables.getLayout()).columnWidths = new int[]
					{ 0, 0, 0, 0 };
					((GridBagLayout) pnlVariables.getLayout()).rowHeights = new int[]
					{ 0, 0, 0 };
					((GridBagLayout) pnlVariables.getLayout()).columnWeights = new double[]
					{ 0.0, 1.0, 0.0, 1.0E-4 };
					((GridBagLayout) pnlVariables.getLayout()).rowWeights = new double[]
					{ 1.0, 0.0, 1.0E-4 };

					// ======== scVariables ========
					{
						scVariables.setViewportView(tbVariables);
					}
					pnlVariables.add(scVariables, new GridBagConstraints(0, 0,
							3, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0,
							0));

					// ---- btnRefreshVariables ----
					btnRefreshVariables.setText("Aktualisieren");
					pnlVariables.add(btnRefreshVariables,
							new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER,
									GridBagConstraints.BOTH, new Insets(0, 0,
											0, 5), 0, 0));

					// ---- btnAddVariable ----
					btnAddVariable.setText("+");
					pnlVariables.add(btnAddVariable, new GridBagConstraints(1,
							1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0,
							0));

					// ---- btnRemoveVariable ----
					btnRemoveVariable.setText("-");
					pnlVariables.add(btnRemoveVariable, new GridBagConstraints(
							2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
							0));
				}
				pnlOverview.add(pnlVariables, new GridBagConstraints(2, 2, 1,
						1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			tbpMain.addTab("\u00dcbersicht", pnlOverview);

			// ======== pnlDeployedDefinitions ========
			{
				pnlDeployedDefinitions.setLayout(new GridBagLayout());
				((GridBagLayout) pnlDeployedDefinitions.getLayout()).columnWidths = new int[]
				{ 0, 0, 0 };
				((GridBagLayout) pnlDeployedDefinitions.getLayout()).rowHeights = new int[]
				{ 0, 0, 0 };
				((GridBagLayout) pnlDeployedDefinitions.getLayout()).columnWeights = new double[]
				{ 1.0, 0.0, 1.0E-4 };
				((GridBagLayout) pnlDeployedDefinitions.getLayout()).rowWeights = new double[]
				{ 1.0, 0.0, 1.0E-4 };

				// ======== scDeployedProcessDefinitions ========
				{
					scDeployedProcessDefinitions
							.setViewportView(tbDeployedProcessDefinitions);
				}
				pnlDeployedDefinitions.add(scDeployedProcessDefinitions,
						new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

				// ---- btnStartInstance ----
				btnStartInstance.setText("Instanz starten");
				pnlDeployedDefinitions.add(btnStartInstance,
						new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

				// ---- btnRefreshDefinitions ----
				btnRefreshDefinitions.setText("Aktualisieren");
				pnlDeployedDefinitions.add(btnRefreshDefinitions,
						new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
			}
			tbpMain.addTab("Prozess-Definitionen", pnlDeployedDefinitions);
		}
		contentPane.add(tbpMain, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	private JToolBar tbMain;
	private JButton btnRefreshProcess;
	private JButton btnSizeUp;
	private JButton btnSizeDown;
	private JButton btnResetSize;
	private JTabbedPane tbpMain;
	private JPanel pnlOverview;
	private JPanel bpmnView;
	private JScrollPane bpmnScroller;
	private BpmnPanel bpmnPanel;
	private JLabel lblError;
	private JPanel pnlActualTasks;
	private JScrollPane scActualTasks;
	private GenericBpmnTable tbActualTasks;
	private JButton btnCompleteTask;
	private JPanel pnlRunningInstances;
	private JScrollPane scRunningInstances;
	private GenericBpmnTable tbRunningInstances;
	private JButton btnRefreshInstances;
	private JPanel pnlVariables;
	private JScrollPane scVariables;
	private GenericBpmnTable tbVariables;
	private JButton btnRefreshVariables;
	private JButton btnAddVariable;
	private JButton btnRemoveVariable;
	private JPanel pnlDeployedDefinitions;
	private JScrollPane scDeployedProcessDefinitions;
	private GenericBpmnTable tbDeployedProcessDefinitions;
	private JButton btnStartInstance;
	private JButton btnRefreshDefinitions;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
