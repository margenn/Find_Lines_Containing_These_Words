/*
 * Do a Hypersearch and return only lines that contains all words entered in the dialog
 * If 2 words, search them by proximity (100 chars) in multiline mode, either
 * @version: 1.0.1
*/

import javax.swing.border.*;

void dbg(String msg) {
	Log.log(Log.WARNING, BeanShell.class, "MacroDebug: " + msg);
}

void showDialog() {
	title = "Find all lines containing these words";
	dialog = new JDialog(view, title, false);
	content = new JPanel(new BorderLayout());
	content.setBorder(new EmptyBorder(5, 5, 5, 5));
	dialog.setContentPane(content);

	containerPanel = new JPanel(new GridLayout(3, 1)); content.add(containerPanel);

	titleLabel = new JLabel("Uma Palavra: Procura no TODO. Duas ou mais: Procura linhas contendo todas");
	containerPanel.add(titleLabel, BorderLayout.NORTH);

	pwPanel = new JPanel(new GridLayout(1, 1));
	txtField = new JTextField(); pwPanel.add(txtField);
	containerPanel.add(pwPanel, BorderLayout.CENTER);

	buttonPanel = new JPanel(new GridLayout(1, 2));
	buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
	okButton = new JButton("OK"); buttonPanel.add(okButton);
	cancelButton = new JButton("Cancel"); buttonPanel.add(cancelButton);
	containerPanel.add(buttonPanel, BorderLayout.SOUTH);

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

	// Close the dialog if user presses ESC. keyTyped is necessary, otherwise no typing will be recognized. keyReleased dont know if really necessary.
	txtField.addKeyListener(this);
	void keyPressed(evt) { if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) { dialog.dispose(); } }
	void keyReleased(evt) {}
	void keyTyped(evt) {}

	dialog.pack();
	dialog.setLocationRelativeTo(view);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
}

void searchLinesContainingTheseWords(String str) {
	if (str == null || str.trim().length() == 0 ) { return; }
	String[] words = str.split("\\s+");
	String regex = "";
	if (words.length == 1) {
		msg = "You must enter at least TWO words.";
		Macros.message(view, msg);
	} else {
		regex = "^";
		for (int i=0; i<words.length; i++) {
			regex = regex + "(?=.*" + words[i] + ")";
		}
		regex = regex + ".*$";
		// if 2 words, search multiline either with 100 chars distance between words
		if (words.length == 2) {
			regex = "(" + regex + "|(?s)" + words[0] + ".{0,100}" + words[1] + "|(?s)" + words[1] + ".{0,100}" + words[0] + ")";
		}
	}

	// Save configurations
	boolean beanshellreplace = SearchAndReplace.getBeanShellReplace();
	boolean ignoreCase = SearchAndReplace.getIgnoreCase();
	boolean regexp = SearchAndReplace.getRegexp();

	// Apply temporary search settings
	SearchAndReplace.setBeanShellReplace(false);
	SearchAndReplace.setIgnoreCase(true);
	SearchAndReplace.setRegexp(true);

	// Do the searching
	SearchAndReplace.setSearchString(regex);
	SearchAndReplace.setSearchFileSet(new CurrentBufferSet());
	SearchAndReplace.hyperSearch(view, false);

	// Restore
	SearchAndReplace.setBeanShellReplace(beanshellreplace);
	SearchAndReplace.setIgnoreCase(ignoreCase);
	SearchAndReplace.setRegexp(regexp);
}

void dd(String msg) {
	Log.log(Log.DEBUG, BeanShell.class, "DebugMsg: " + msg);
}

showDialog();
