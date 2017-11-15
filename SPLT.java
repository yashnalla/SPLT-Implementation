package SPLT_A4;

public class SPLT implements SPLT_Interface{
  private BST_Node root;
  private int size;
  
  public SPLT() {
    this.size = 0;
    root = null;
  } 
  
  public BST_Node getRoot() { //please keep this in here! I need your root node to test your tree!
    return root;
  }

@Override
public void insert(String s) {
	// TODO Auto-generated method stub
	if (root == null) {
		root = new BST_Node(s);
		size += 1;
		//return true;
	}
	
	else {
		if(s.compareTo(root.getData())!= 0){
			BST_Node a = root.insertNode(s);
			root = root.splay(a);
			size++;
		}else{
			return;
		}
		//return root.insertNode(s);
	}
}

@Override
public void remove(String s) {
	// TODO Auto-generated method stub
	if(size ==1 && root.data.compareTo(s) ==0 ){
		root = null;
		size --;
	}else{
		contains(s);
		root.removeNode(s);
		size--;
	}
}

@Override
public String findMin() {
	// TODO Auto-generated method stub
	if (empty()) {
		return null;
	} else {
		BST_Node a = root.findMin();
		root =root.splay(a);
		return root.data;
	}
}

@Override
public String findMax() {
	// TODO Auto-generated method stub
	if (empty()) {
		return null;
	} else {
		BST_Node a = root.findMax();
		root =root.splay(a);
		return root.data;
	}
}

@Override
public boolean empty() {
	// TODO Auto-generated method stub
	if(size()==0){
		return true;
	}
	return false;
}

@Override
public boolean contains(String s) {
	// TODO Auto-generated method stub
	if (empty() && root == null) {
		return false;
	}else{
		BST_Node a = root.containsNode(s);
		root = root.splay(a);
		
		if(s.compareTo(a.getData())==0){
			return true;
		}else{
			return false;
		}
	}
}
public boolean contain(String s){
	if (empty()) {
		return false;
	}
    BST_Node a = root.containsNode(s);
	
	if(a == null){
		return false;
	}else{
		return true;
	}
}

@Override
public int size() {
	// TODO Auto-generated method stub
	return size;
}

@Override
public int height() {
	// TODO Auto-generated method stub
	if (empty()) {
		return -1;
	} else {
		return root.getHeight();
	}
}
  

}