package data;

import java.io.Serializable;

/**
 * An instance of this class represent one test-item from the list of one test.
 * The test-item belongs to some test and have a matching template-item in the
 * template of that test. (Example: Test in date 20.08.15, from Template School. 
 * Then TestItem can match ceilings (->TemplateItem) in the School template).
 * @author Shira Elitzur
 */
public class TestItem implements Serializable {
    private int itemNumber;
    private String title;
    private String info;
    private String category;
    private int levelNumber = 5;
    /**
     * The comment that the user added to this testItem
     */
    private String commnet;
    /**
     * For GUI purposes. 'first' is true in the creation of the instance and turn to
     * false with the first assignment to 'ok'. When first is true the ok/notOk buttons
     * both appear with grey background
     */
    private boolean first;
    
    /**
     * 
     */
    private boolean level = false;
    
    /**
     * Indicates whether the item is ok or not.
     */
    private boolean ok;
    /**
     * The testChapter that the item belongs to- i think that i am not using that
     */
    private TestChapter chapter;
    /**
     * The matching template-item of this test-item
     */
    private TemplateItem templateItem;
    
    private byte[] image1;
    private String image1Code;
    private byte[] image2;
    private String image2Code;
    
    
	public TestItem( ) {
		first = true;
    }
	
    public TemplateItem getTemplateItem() {
		return templateItem;
	}

	public void setTemplateItem(TemplateItem templateItem) {
		this.templateItem = templateItem;
	}

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCategory() {
        return category;
    }

    public void setChapter(TestChapter chapter) {
        this.chapter = chapter;
    }

    public String getCommnet() {
        return commnet;
    }

    public void setCommnet(String commnet) {
        this.commnet = commnet;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
    	first = false;
        this.ok = ok;
    }
    
    public boolean isFirst() {
    	return first;
    }
    
    public void setFirst( boolean first ) {
    	this.first = first;
    }
    
    @Override
    public String toString() {
    	return "Item number: " + getItemNumber() +"\nItem title: " + getTitle() +
    			"\nStatus: " + isOk() + "\n Image: " /* + getImage()*/;
    }
    
    public boolean isValid() {
    	// first is true before the user choose ok/not ok
    	if( first == true ) {
    		return false;
    	}
    	
    	// if the item is signed as not ok
    	// it must have a comment or image
    	if( !isOk() ) {
    		if( ( commnet == null || commnet.length() == 0 ) &&
    				image1Code == null && image2Code == null  ) { // the image is in comment for now but it should be image/comment
    			return false;
    		}
    	}
    	
    	return true;
    }

	public byte[] getImage1() {
		return image1;
	}

	public void setImage1(byte[] image) {
		this.image1 = image;
	}

	public byte[] getImage2() {
		return image2;
	}

	public void setImage2(byte[] image2) {
		this.image2 = image2;
	}

	public String getImage1Code() {
		return image1Code;
	}

	public void setImage1Code(String image1Code) {
		this.image1Code = image1Code;
	}

	public String getImage2Code() {
		return image2Code;
	}

	public void setImage2Code(String image2Code) {
		this.image2Code = image2Code;
	}

	public boolean isLevel() {
		return level;
	}

	public void setLevel(boolean level) {
		this.level = level;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
    
}
