/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import javax.swing.JInternalFrame;

/**
 *
 * @author Kasun
 */
public class MyInternalFrame extends JInternalFrame{
    static final int xPosition = 30, yPosition = 30;
		public MyInternalFrame() {
			super("IFrame #" , true, // resizable
					true, // closable
					true, // maximizable
					true);// iconifiable
			setSize(300, 300);
			// Set the window's location.
			
		}
    
}
