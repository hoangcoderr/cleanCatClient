package cleanCatClient.gui.clickgui.components.clickguicomp;

import cleanCatClient.gui.clickgui.ClickGui;

import java.util.ArrayList;

public class CategoryManager {
	
	public static int currentPage = 0;
	public static boolean openDragScreen = false;
	
	public static void thisPage(int number) {
		currentPage=number;
		ArrayList<ClickGuiCategoryButton> category = ClickGui.getClickGuiCategoryButton();
		
		for(int i = 0; i< category.size();i++) {
			if(i != currentPage) {
				category.get(i).setIsOnThisPage(false);
			}
		}
		
		if(currentPage == 4) {
			currentPage = 0;
			openDragScreen = true;
		}
	}


	
	
	
}
