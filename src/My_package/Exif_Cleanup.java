package My_package;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Exif_Cleanup {
	
	
	static String imagePath;
	static JFrame boxFrame = new JFrame();
	static JPanel boxPanel = new JPanel();
	static File imageFile;
	static String imageFolder;
	static JLabel boxHeader = new JLabel("Choose a image file");
	static JButton remove;

	public static void main(String[] args) {
		selectImageFile();

	}
	
	
	
	public static void selectImageFile() {
	   remove = new JButton("Remove EXIF");
		remove.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 cleanUpExifImage();
	         }          
	    });
		JButton select_file = new JButton("Choose Image File");
		select_file.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	            JFileChooser chooser = new JFileChooser();
	            if(imageFolder == null) {
	            	chooser.setCurrentDirectory(new java.io.File("."));
	            } else {
	            	chooser.setCurrentDirectory(new java.io.File(imageFolder));
	            }
	            chooser.setDialogTitle("");
	            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	            chooser.setAcceptAllFileFilterUsed(false);
	            
	            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	                imagePath = chooser.getSelectedFile().toString();
	                imageFile = chooser.getSelectedFile();
	                boxHeader.setText("Prepared to cleanUp EXIF!!");
	               
	                select_file.setVisible(false);
	            } else {
	                boxHeader.setText("Not choosen image file!! Please Try again.");
	            }
	        }
	    });
		
		
		boxPanel.setPreferredSize(new Dimension(500, 100));
		boxPanel.add(boxHeader);
		boxPanel.add(select_file);
		boxPanel.add(remove);
		boxFrame.setContentPane(boxPanel);
		boxFrame.pack();
		boxFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boxFrame.setLocationRelativeTo(null);
		boxFrame.setVisible(true);
		boxFrame.setTitle("EXIF Cleaned");
	}
	
	
	
	public static void cleanUpExifImage() {
		if(imagePath == null) {
			JOptionPane.showMessageDialog(null, "Uh-oh! You didn't choose anything!", "Oops!", JOptionPane.WARNING_MESSAGE);
		} else {
			String extension = "";
			int i = imageFile.getName().lastIndexOf('.');
			if (i > 0) {
			    extension = imageFile.getName().substring(i+1);
			    BufferedImage image;
				try {
					image = ImageIO.read(imageFile);
					ImageIO.write(image, extension, new File(imagePath));  
					cleanUpsuccess();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Unexpected mishap happened. Is this a image file?", "Error!", JOptionPane.ERROR_MESSAGE);
				}  
			} else {
				JOptionPane.showMessageDialog(null, "There is no extension on this file. Try once more.", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void cleanUpsuccess() {
		
		boxHeader.setText("Successfully CleanUp Image EXIF.Go and check image file, Thank You");
		imagePath = null;
		remove.setVisible(false);
		imageFolder = imageFile.getPath().toString().replaceAll(imageFile.getName().toString(), "");
		imageFile = null;
	}

}
