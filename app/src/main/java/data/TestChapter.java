package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shira Elitzur
 */
public class TestChapter implements Serializable{
	private List<TestItem> items;
	private TemplateChapter template;
	
    public TestChapter() {
    	items = new ArrayList<TestItem>();
    }
    


    public TemplateChapter getTemplate() {
		return template;
	}



	public void setTemplate(TemplateChapter template) {
		this.template = template;
	}



	public List<TestItem> getItems() {
        return items;
    }

    public void setItems(List<TestItem> items) {
        this.items = items;
    }
    
    public void initItems() {
    	items = new ArrayList<TestItem>(template.getItems().size());
    	TestItem item;
    	for( int i = 0; i < template.getItems().size(); i++ ) {
    		items.add(i, new TestItem());
    		item = items.get(i);
    		item.setTemplateItem( template.getItems().get(i));
    	}
    }
    
    public boolean isValid() {
    	if (items.size() == 0) {
    		return false;
    	}
    	
    	for ( TestItem item : items ) {
    		if(!item.isValid()) {
    			return false;
    		}
    	}
    	
    	return true;
    }
    

}
