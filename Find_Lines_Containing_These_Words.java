/*
 * Find_Lines_Containing_These_Words.bsh - a BeanShell macro for jEdit
 * Do a Hypersearch and return only lines that contains all words entered in the dialog
 * @author: Marcelo Gennari
 * @version: 1.0.0
*/

import javax.swing.border.*;

void showDialog() {
	title = "Find all lines containing these words";
	dialog = new JDialog(view, title, false);
	content = new JPanel(new BorderLayout());
	content.setBorder(new EmptyBorder(5, 5, 5, 5));
	dialog.setContentPane(content);
	pwPanel = new JPanel(new GridLayout(1, 2));
	pwLabel = new JLabel("Enter space separated words:");
	txtField = new JTextField();
	pwPanel.add(pwLabel);
	pwPanel.add(txtField);
	content.add(pwPanel, BorderLayout.CENTER);
	buttonPanel = new JPanel(new GridLayout(1, 2));
	buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
	okButton = new JButton("OK");
	cancelButton = new JButton("Cancel");
	buttonPanel.add(okButton);
	buttonPanel.add(cancelButton);
	content.add(buttonPanel, BorderLayout.SOUTH);
	okButton.addActionListener(this);
	cancelButton.addActionListener(this);
	dialog.getRootPane().setDefaultButton(okButton);
	actionPerformed(e) {
		this.dialog.dispose();
		cmd = e.getActionCommand();
		if(cmd.equals("OK")) {
			searchLinesContainingTheseWords(this.txtField.getText().trim());
		}
		return;
	}
	dialog.pack();
	dialog.setLocationRelativeTo(view);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
}

void searchLinesContainingTheseWords(String str) {
	if (str == null || str.trim().length() == 0 ) { return; }

	String[] words = str.split("\\s+");
	String regex = "^";
	for (int i=0; i<words.length; i++) {
		regex = regex + "(?=.*" + words[i] + ")";
	}
	regex = regex + ".*$";
	SearchAndReplace.setSearchString(regex);

	// Save configurations
	boolean beanshellreplace = SearchAndReplace.getBeanShellReplace();
	boolean ignoreCase = SearchAndReplace.getIgnoreCase();
	boolean regexp = SearchAndReplace.getRegexp();

	// Aplica as configuracoes temporarias
	SearchAndReplace.setBeanShellReplace(false);
	SearchAndReplace.setIgnoreCase(true);
	SearchAndReplace.setRegexp(true);
	SearchAndReplace.setSearchFileSet(new CurrentBufferSet());
	if (textArea.getSelection().length != 0) {
		SearchAndReplace.hyperSearch(view, true);
	} else {
		SearchAndReplace.hyperSearch(view, false);
	}

	// Restore
	SearchAndReplace.setBeanShellReplace(beanshellreplace);
	SearchAndReplace.setIgnoreCase(ignoreCase);
	SearchAndReplace.setRegexp(regexp);
}

void ll(String msg) {
	Log.log(Log.DEBUG, BeanShell.class, "DebugMsg: " + msg);
}

showDialog();
