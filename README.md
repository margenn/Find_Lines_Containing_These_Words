# Find_Lines_Containing_These_Words
jEdit Macro - Hypersearch and return only lines that contains all words entered in the dialog

### Installation:


Just change the extension from .java to .bsh and drop it in the following path:


```
jedit
│   jedit.jar
│   ...
│
└───macros
    │
    └───text (on any other of your preference)
        │   Find_Lines_Containing_These_Words.bsh
        │   ...
```

Macros > Rescan Macros



### Associate with a keyboard shortcut (Eg: F7):

Utilities > Global Options > Shortcuts > Action Set "Macros" > Filter "Find These" > Choose your primary shortcut (Eg: F7)



### Usage:

Press your shortcut "F7" (or any other of your choice)

Write any number of words separated by space and press ENTER

The editor will do a hypersearch and return all lines containg ALL words entered in any order. Case insensitive.

If you type TWO words, the hypersearch will include multiline search. It will search all text where the distance between words is 100 chars regardless how many line breaks are between them.


### Example:

Let's suppose you have an text with:
<pre><code>The oranges are ready to be picked.
Oranges, apples and bananas are out of stock.
Strawberries and cherries are red.
The apples and the oranges are ripe.
</code></pre>

Type  **"orang appl"**  in the macro dialog box and the editor will return these lines:

<pre><code><b>Orang</b>es, <b>appl</b>es and bananas are out of stock.
The <b>appl</b>es and the <b>orang</b>es are ripe.
</code></pre>
