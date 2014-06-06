/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package gui;

/*
 * This code is based on an example provided by Richard Stanford, 
 * a tutorial reader.
 */

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

import drawableObjects.DrawingBoardContent;
import drawableObjects.Furniture;

/**
 * Classe utilisant un JTreee et permettant sa modification en cours de programme.
 */
public class DynamicTree extends JPanel implements  DrawingBoardContentObserver {
    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    
    private AdditionListener additionListener = new AdditionListener();
    private DeletionListener deletionListener = new DeletionListener();
    private ModificationListener modificationListener = new ModificationListener();

    public DynamicTree() {
        super(new GridLayout(1,0));
        
        rootNode = new DefaultMutableTreeNode("Used furnitures");
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }

    /** Remove all nodes except the root node. */
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    /** Remove the currently selected node. */
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }

        // Either there was no selection, or the root was selected.
    }

    /** Add child to the currently selected node. */
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode)(parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child) {
        return addObject(parent, child, false);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child, 
                                            boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

        if (parent == null) {
            parent = rootNode;
        }
	
        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }
    
    private class AdditionListener implements Observer {

		@Override
		public void update(Observable o, Object arg) {
			System.out.println("add jtree");
			//crée le JTreeNode du meuble et l'ajoute au JTree
			((Furniture)arg).setJTreeNode(addObject(((Furniture)arg).getLibrary().getJTreeNode(), ((Furniture)arg).getName(), true));
			//((Furniture)arg).setJTreeNode(addObject(rootNode, ((Furniture)arg).getName(), true));
			//treeModel.reload();
//			int indices[] = new int[]{((Furniture)arg).getLibrary().getJTreeNode().getLeafCount()};
//			treeModel.nodesWereInserted(((Furniture)arg).getLibrary().getJTreeNode(),indices);
			//treeModel.nodeChanged(((Furniture)arg).getJtreeNode());
		}
    	
    }
    
    private class DeletionListener implements Observer {

		@Override
		public void update(Observable o, Object arg) {
			//supprime le JTreeNode du meuble
			DynamicTree.this.tree.setSelectionPath(new TreePath(((Furniture)arg).getJtreeNode().getPath()));
			DynamicTree.this.removeCurrentNode();
		}
    	
    }
    
    private class ModificationListener implements Observer {

		@Override
		public void update(Observable o, Object arg) {
			//modifie le JTreeNode du meuble
			((Furniture)arg).getJtreeNode().setUserObject(((Furniture)arg).getName());
			treeModel.nodeChanged(((Furniture)arg).getJtreeNode());
		}
    	
    }

	@Override
	public Observer getAdditionObserver() {
		return additionListener;
	}

	@Override
	public Observer getDeletionObserver() {
		return deletionListener;
	}

	@Override
	public Observer getModificationObserver() {
		return modificationListener;
	}
}
