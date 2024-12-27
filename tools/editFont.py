from fontTools.ttLib import TTFont
import tkinter as tk
from tkinter import filedialog, ttk
from typing import Dict

class FontMapViewer:
    def __init__(self):
        self.root = tk.Tk()
        self.root.title("Font Character Mapping Viewer")
        self.root.geometry("800x600")
        
        # Create UI elements
        self.setup_ui()
        
    def setup_ui(self):
        # File selection
        select_btn = ttk.Button(self.root, text="Select Font File", command=self.load_font)
        select_btn.pack(pady=10)
        
        # Create frame for character display
        self.char_frame = ttk.Frame(self.root)
        self.char_frame.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)
        
        # Scrollable text widget
        self.text_widget = tk.Text(self.char_frame, wrap=tk.WORD, width=80, height=30)
        scrollbar = ttk.Scrollbar(self.char_frame, command=self.text_widget.yview)
        self.text_widget.configure(yscrollcommand=scrollbar.set)
        
        self.text_widget.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)
        scrollbar.pack(side=tk.RIGHT, fill=tk.Y)

    def load_font(self):
        filepath = filedialog.askopenfilename(
            filetypes=[("TrueType Fonts", "*.ttf"), ("All Files", "*.*")]
        )
        if filepath:
            self.display_char_mapping(filepath)

    def display_char_mapping(self, font_path: str):
        try:
            font = TTFont(font_path)
            cmap = font.getBestCmap()
            
            self.text_widget.delete(1.0, tk.END)
            self.text_widget.insert(tk.END, f"Font: {font_path}\n\n")
            
            line_count = 1
            for unicode_value, glyph_name in sorted(cmap.items()):
                try:
                    char = chr(unicode_value)
                    self.text_widget.insert(tk.END, 
                        f"{line_count:4d}. Unicode: U+{unicode_value:04X} | Char: {char} | Glyph: {glyph_name}\n")
                    line_count += 1
                except:
                    continue
                    
            font.close()
            
            # Display total count
            self.text_widget.insert(tk.END, f"\nTotal characters: {line_count-1}")
            
        except Exception as e:
            self.text_widget.delete(1.0, tk.END)
            self.text_widget.insert(tk.END, f"Error loading font: {str(e)}")

    def run(self):
        self.root.mainloop()

if __name__ == "__main__":
    viewer = FontMapViewer()
    viewer.run()