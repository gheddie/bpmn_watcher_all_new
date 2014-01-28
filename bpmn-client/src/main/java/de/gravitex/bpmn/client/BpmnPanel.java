package de.gravitex.bpmn.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.repository.DiagramLayout;
import org.camunda.bpm.engine.repository.DiagramNode;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import de.gravitex.bpmn.client.singleton.ProcessingSingleton;
import de.gravitex.bpmn.server.dto.JobExecutionDTO;

public class BpmnPanel extends JPanel implements MouseMotionListener, MouseListener {
	
	private static final long serialVersionUID = 1L;

	private static Logger		logger								= Logger.getLogger(BpmnPanel.class);

	private Image img;

	private HashMap<String, DiagramNodeDimension> nodeDimensions;

	private double scaleFactor;

	private List<String> activities;

	private List<JobExecutionDTO> jobs;
	
	private static final int ELEMENT_TRACKING_INSET = 5;
	
	private static final int ELEMENT_SELECTION_INSET = 7;

	private static final Color COLOR_TRACK_ELEMENT = Color.BLUE;
	
	private static final Color COLOR_SELECT_ELEMENT = Color.RED;

	private static final double ZOOM_STEP = 0.1d;
	
	public BpmnPanel() {
		setLayout(null);
		scaleFactor = 1.0d;
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public void paintComponent(Graphics g) {
		
		((Graphics2D) g).scale(scaleFactor, scaleFactor);
		
		g.drawImage(img, 0, 0, null);
		
		//render selections
		if (nodeDimensions != null) {
			for (DiagramNodeDimension nd : nodeDimensions.values()) {
				if (nd.isSelected()) {
					Color oldColor = g.getColor();
					g.setColor(COLOR_SELECT_ELEMENT);
					g.drawRect(nd.getX().intValue() - ELEMENT_SELECTION_INSET, nd.getY().intValue() - ELEMENT_SELECTION_INSET, nd.getWidth().intValue() + 2*ELEMENT_SELECTION_INSET, nd.getHeight().intValue() + 2*ELEMENT_SELECTION_INSET);
					g.setColor(oldColor);
				}
			}	
		}

		//activities
		if ((activities != null) && (activities.size() > 0)) {
			for (String activity : activities) {
				//try to match it on a location
				DiagramNodeDimension nd = nodeDimensions.get(activity);
				if (nd != null) {
					Color oldColor = g.getColor();
					g.setColor(COLOR_TRACK_ELEMENT);
					g.drawRect(nd.getX().intValue() - ELEMENT_TRACKING_INSET, nd.getY().intValue() - ELEMENT_TRACKING_INSET, nd.getWidth().intValue() + 2*ELEMENT_TRACKING_INSET, nd.getHeight().intValue() + 2*ELEMENT_TRACKING_INSET);
					g.setColor(oldColor);
				}
			}
		}
		
		//jobs
		if ((jobs != null) && (jobs.size() > 0)) {
			for (JobExecutionDTO dto : jobs) {
				DiagramNodeDimension nd = nodeDimensions.get(dto.getActivityId());
				if (nd != null) {
					g.drawString(dto.formatRemainingTime(), nd.getX().intValue(), nd.getY().intValue());
				}
			}	
		}
	}

	public void initProcess(ProcessInstance processInstance) {
		try {
			ImageIcon processDiagram = ProcessingSingleton.getInstance().getProcessDiagram(processInstance.getProcessDefinitionId());
			if (processDiagram == null) {
				System.out.println("process digram is NULL --> returning.");
				return;
			}
			this.img = processDiagram.getImage();
			Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
			nodeDimensions = new HashMap<>();
			
			//query (and cache) node coordinates
			DiagramLayout processDiagramLayout = ProcessingSingleton.getInstance().getDiagramLayout(processInstance.getProcessDefinitionId());
			List<DiagramNode> nodes = processDiagramLayout.getNodes();
			logger.trace(" --------- caching nodes --------- "); 
			for (DiagramNode node : nodes) {
				nodeDimensions.put(node.getId(), new DiagramNodeDimension(node.getX(), node.getY(), node.getWidth(), node.getHeight()));
				logger.trace("caching node by id : "+node.getId()+".");
			}
			logger.trace(" --------- caching nodes end --------- ");
			this.activities = ProcessingSingleton.getInstance().queryActivities(processInstance.getProcessInstanceId());			
			this.jobs = ProcessingSingleton.getInstance().queryJobs(processInstance.getProcessInstanceId());
			repaint();
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
	}

	public void increaseZoom() {
		scaleFactor += ZOOM_STEP;
		repaint();
	}

	public void decreaseZoom() {
		scaleFactor -= ZOOM_STEP;
		repaint();
	}

	public void resetZoom() {
		scaleFactor = 1.0d;
		repaint();
	}
	
	// --------- EVENT METHODS (MOUSE MOTION) ---------

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseMoved(MouseEvent e) {
//		System.out.println("[x:"+e.getX()+"|y:"+e.getY()+"]");
		if (nodeDimensions != null) {
			for (DiagramNodeDimension nd : nodeDimensions.values()) {
				if (nd.includes(e.getX(), e.getY())) {
					nd.setSelected(true);
				} else {
					nd.setSelected(false);
				}
				repaint();
			}	
		}
	}

	// --------- EVENT METHODS (MOUSE) ---------
	
	public void mouseClicked(MouseEvent e) {
		int selectionCounter = 0;
		for (DiagramNodeDimension nd : nodeDimensions.values()) {
			if(nd.isSelected()) {
				System.out.println(nd);
				selectionCounter++;
			}
		}		
		System.out.println("selection counter : " + selectionCounter);
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
