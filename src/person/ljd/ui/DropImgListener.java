package person.ljd.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class DropImgListener implements DropTargetListener {


	public DropImgListener(PicCutFrame cutFrame) {
		this.cutFrame = cutFrame;
	}

	@Override
	public void dragEnter(DropTargetDragEvent event) {
	      if (!isDragAcceptable(event))
	      {  event.rejectDrag();
	         return;
	      }
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropActionChanged(DropTargetDragEvent event) {
		if (!isDragAcceptable(event))
	      {  event.rejectDrag();
	         return;
	      }
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drop(DropTargetDropEvent event) {
		if (!isDropAcceptable(event))
	      {  event.rejectDrop();
	         return;
	      }

	      event.acceptDrop(DnDConstants.ACTION_COPY);

	      Transferable transferable = event.getTransferable();

	      DataFlavor[] flavors
	         = transferable.getTransferDataFlavors();
	      for (int i = 0; i < flavors.length; i++)
	      {  DataFlavor d = flavors[i];
	         try
	         {  if (d.equals(DataFlavor.javaFileListFlavor))
	            {  java.util.List fileList = (java.util.List)
	                  transferable.getTransferData(d);
	               Iterator iterator = fileList.iterator();
	               while (iterator.hasNext())
	               {  File f = (File)iterator.next();
	               		String reg="(?i).+?\\.(jpg|jpeg|gif|png)";
	               		if(f.getName().matches(reg)){
	               			cutFrame.showImage(f.getAbsolutePath());
	               		}
	                  break;
	               }
	            }
	         }
	         catch(Exception e)
	         {  //textArea.append("Error: " + e + "\n");
	        	 JOptionPane.showMessageDialog(null, 
	        			 "Error: " + e + "\n","错误",JOptionPane.ERROR_MESSAGE);        	 
	         }
	     }
	     event.dropComplete(true);	         
	}
   private boolean isDragAcceptable(DropTargetDragEvent event)
   {  // usually, you check the available data flavors here
      // in this program, we accept all flavors
      return (event.getDropAction()
         & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
   }
   public boolean isDropAcceptable(DropTargetDropEvent event)
   {  // usually, you check the available data flavors here
      // in this program, we accept all flavors
      return (event.getDropAction()
         & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
   }   
   private PicCutFrame cutFrame;
}
