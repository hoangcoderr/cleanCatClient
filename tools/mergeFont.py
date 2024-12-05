from fontTools.ttLib import TTFont

# Mở hai file TTF
font1 = TTFont("1.ttf")
font2 = TTFont("2.ttf", fontNumber=0)  # Specify the font number for the TTC file

# Lấy danh sách các ký tự trong mỗi file
glyphs1 = set(font1.getGlyphOrder())
glyphs2 = set(font2.getGlyphOrder())

# Lấy bảng cmap từ font2
cmap_table = font2['cmap'].getBestCmap()

# Tìm các glyph có trong font2 mà không có trong font1
missing_glyphs = glyphs2 - glyphs1

# Hàm để thêm glyph và các thành phần của nó nếu là glyph composite
def add_glyph_and_components(glyph_name):
    # Nếu glyph_name đã có trong font1, bỏ qua
    if glyph_name in font1['glyf']:
        return
    
    # Thêm glyph vào font1 từ font2
    font1['glyf'][glyph_name] = font2['glyf'][glyph_name]
    
    # Kiểm tra nếu glyph này là composite
    glyph = font2['glyf'][glyph_name]
    if glyph.isComposite():
        for component in glyph.components:
            # Đảm bảo các thành phần của composite glyph cũng có mặt trong font1
            add_glyph_and_components(component.glyphName)
    
    # Thêm vào hmtx để tránh lỗi bảng hmtx
    font1['hmtx'].metrics[glyph_name] = font2['hmtx'].metrics.get(glyph_name, (600, 0))

# Thêm glyphs từ font2 vào font1 và cập nhật cmap cho font1
for unicode_value, glyph_name in cmap_table.items():
    if glyph_name in missing_glyphs:
        add_glyph_and_components(glyph_name)
        
        # Chỉ thêm mapping vào cmap của font1 nếu unicode_value hợp lệ
        if unicode_value <= 65535:
            for cmap in font1['cmap'].tables:
                cmap.cmap[unicode_value] = glyph_name

# Cập nhật bảng maxp để phản ánh số lượng glyph mới
font1['maxp'].numGlyphs = len(font1['glyf'])

# Lưu font1 với các glyph đã thêm
font1.save("1_modified.ttf")