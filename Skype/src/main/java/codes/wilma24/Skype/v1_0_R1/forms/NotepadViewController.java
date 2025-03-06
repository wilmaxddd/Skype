package codes.wilma24.Skype.v1_0_R1.forms;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NotepadViewController extends JDialog {

	private JTextArea textArea = new JTextArea(32, 56);

	private JTextArea textArea2 = new JTextArea(32, 56);

	private boolean retain = false;

	public NotepadViewController(JFrame parent, String title, String text,
			OutputStream fos, Runnable callback) {
		super(parent, title, true);
		Font font = getLucidaConsoleFont(14);
		textArea.setFont(font);
		textArea.setText(text);
		textArea2.setText(text);
		textArea.setLineWrap(true);
		textArea2.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		this.setContentPane(scrollPane);
		this.pack();
		this.setLocationRelativeTo(parent);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int res = JOptionPane
						.showConfirmDialog(parent,
								"Do you want to save changes to document?",
								title, JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (res == 0) {
					try {
						fos.write(textArea.getText().getBytes());
						fos.flush();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					callback.run();
				} else if (retain) {
					try {
						fos.write(textArea2.getText().getBytes());
						fos.flush();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				super.windowClosing(e);
			}
		});
	}

	public NotepadViewController(JDialog parent, String title, String text,
			OutputStream fos, Runnable callback) {
		super(parent, title, true);
		Font font = getLucidaConsoleFont(14);
		textArea.setFont(font);
		textArea.setText(text);
		textArea2.setText(text);
		textArea.setLineWrap(true);
		textArea2.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		this.setContentPane(scrollPane);
		this.pack();
		this.setLocationRelativeTo(parent);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int res = JOptionPane
						.showConfirmDialog(parent,
								"Do you want to save changes to document?",
								title, JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (res == 0) {
					try {
						fos.write(textArea.getText().getBytes());
						fos.flush();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					callback.run();
				} else if (retain) {
					try {
						fos.write(textArea2.getText().getBytes());
						fos.flush();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				super.windowClosing(e);
			}
		});
	}

	public NotepadViewController(JDialog parent, String title, String text,
			Runnable callback) {
		super(parent, title, true);
		Font font = getLucidaConsoleFont(14);
		textArea.setFont(font);
		textArea.setText(text);
		textArea2.setText(text);
		textArea.setLineWrap(true);
		textArea2.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		this.setContentPane(scrollPane);
		this.pack();
		this.setLocationRelativeTo(parent);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int res = JOptionPane
						.showConfirmDialog(parent,
								"Do you want to save changes to document?",
								title, JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (res == 0) {
					callback.text = textArea.getText();
					callback.run();
				}
				super.windowClosing(e);
			}
		});
	}

	public NotepadViewController(JFrame parent, String title, String text,
			Runnable callback) {
		super(parent, title, true);
		Font font = getLucidaConsoleFont(14);
		textArea.setFont(font);
		textArea.setText(text);
		textArea2.setText(text);
		textArea.setLineWrap(true);
		textArea2.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		this.setContentPane(scrollPane);
		this.pack();
		this.setLocationRelativeTo(parent);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int res = JOptionPane
						.showConfirmDialog(parent,
								"Do you want to save changes to document?",
								title, JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (res == 0) {
					callback.text = textArea.getText();
					callback.run();
				}
				super.windowClosing(e);
			}
		});
	}

	public NotepadViewController(JDialog parent, String title, String text) {
		super(parent, title, true);
		Font font = getLucidaConsoleFont(14);
		textArea.setFont(font);
		textArea.setText(text);
		textArea2.setText(text);
		textArea.setLineWrap(true);
		textArea2.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		this.setContentPane(scrollPane);
		this.pack();
		this.setLocationRelativeTo(parent);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public NotepadViewController(JFrame parent, String title, String text) {
		super(parent, title, true);
		Font font = getLucidaConsoleFont(14);
		textArea.setFont(font);
		textArea.setText(text);
		textArea2.setText(text);
		textArea.setLineWrap(true);
		textArea2.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		this.setContentPane(scrollPane);
		this.pack();
		this.setLocationRelativeTo(parent);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public Font getLucidaConsoleFont(int textSize) {
		InputStream is = NotepadViewController.class
				.getResourceAsStream("/lucon.ttf");
		try {
			return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(
					Font.PLAIN, textSize);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return new Font("Lucida Console", Font.PLAIN, textSize);
	}

	public void setText(String text) {
		textArea.setText(text);
		textArea2.setText(text);
	}

	public void setEditable(boolean val) {
		this.textArea.setEditable(val);
		this.textArea2.setEditable(val);
	}

	public void setRetain(boolean retain) {
		this.retain = retain;
	}

	public static abstract class Runnable implements java.lang.Runnable {

		public String text;

	}
}
