package SPLT_A4;

public class BST_Node {
  String data;
  BST_Node left;
  BST_Node right;
  BST_Node par; //parent...not necessarily required, but can be useful in splay tree
  boolean justMade; //could be helpful if you change some of the return types on your BST_Node insert.
            //I personally use it to indicate to my SPLT insert whether or not we increment size.
  BST_Node parent;
  BST_Node grand; 
  BST_Node(String data){ 
    this.data=data;
    this.justMade=true;
  }
  
  BST_Node(String data, BST_Node left,BST_Node right,BST_Node par){ //feel free to modify this constructor to suit your needs
    this.data=data;
    this.left=left;
    this.right=right;
    this.par=par;
    this.justMade=true;
  }

  // --- used for testing  ----------------------------------------------
  //
  // leave these 3 methods in, as is (meaning also make sure they do in fact return data,left,right respectively)

  public String getData(){ return data; }
  public BST_Node getLeft(){ return left; }
  public BST_Node getRight(){ return right; }
  public BST_Node getPar(){ return par;}
  public boolean getMade(){return justMade;}
  
  // --- end used for testing -------------------------------------------

  
  // --- Some example methods that could be helpful ------------------------------------------
  //
  // add the meat of correct implementation logic to them if you wish

  // you MAY change the signatures if you wish...names too (we will not grade on delegation for this assignment)
  // make them take more or different parameters
  // have them return different types
  //
  // you may use recursive or iterative implementations

  /*
  public BST_Node containsNode(String s){ return false; } //note: I personally find it easiest to make this return a Node,(that being the node splayed to root), you are however free to do what you wish.
  public BST_Node insertNode(String s){ return false; } //Really same logic as above note
  public boolean removeNode(String s){ return false; } //I personal do not use the removeNode internal method in my impl since it is rather easily done in SPLT, feel free to try to delegate this out, however we do not "remove" like we do in BST
  public BST_Node findMin(){ return left; } 
  public BST_Node findMax(){ return right; }
  public int getHeight(){ return 0; }

  private void splay(BST_Node toSplay) { return false; } //you could have this return or take in whatever you want..so long as it will do the job internally. As a caller of SPLT functions, I should really have no idea if you are "splaying or not"
                        //I of course, will be checking with tests and by eye to make sure you are indeed splaying
                        //Pro tip: Making individual methods for rotateLeft and rotateRight might be a good idea!
  */

  // --- end example methods --------------------------------------

  
  

  // --------------------------------------------------------------------
  // you may add any other methods you want to get the job done
  // --------------------------------------------------------------------
  public BST_Node containsNode(String s){ //it was me
	  if(data.equals(s)){

		  return this;
	  }
	  if(data.compareTo(s)>0){//s lexiconically less than data
		  if(left==null){

			  return this;
		  }
		  return left.containsNode(s);
	  }
	  if(data.compareTo(s)<0){
		  if(right==null){

			  return this;
		  }
		  return right.containsNode(s);
	  }
	  return this; //shouldn't hit
  }
  public BST_Node insertNode(String s){
		if(data.compareTo(s)>0){
			if(left==null){
				left=new BST_Node(s, null, null, this);
				return splay(left);
			}
			return left.insertNode(s);
		}
		if(data.compareTo(s)<0){
			if(right==null){
				right=new BST_Node(s, null, null, this);
				return splay(right);
			}
			return right.insertNode(s);
		}
		return splay(this);//ie we have a duplicate
	}
  public boolean removeNode(String s){ //DIO
		if(data==null)return false;
		if(data.equals(s)){
			if(left!=null){
				data=left.findMax().data;
				left.removeNode(data);
				if(left.data==null)left=null;
			}
			else if(right!=null){
				data=right.findMin().data;
				right.removeNode(data);
				if(right.data==null)right=null;
			}
			else data=null;
			return true;
		}
		else if(data.compareTo(s)>0){
			if(left==null)return false;
			if(!left.removeNode(s))return false;
			if(left.data==null)left=null;
			return true;
		}
		else if(data.compareTo(s)<0){
			if(right==null)return false;
			if(!right.removeNode(s))return false;
			if(right.data==null)right=null;
			return true;
		}
		return false;
	}
    public BST_Node findMin(){
		if(left!=null){
			return left.findMin();
		}
		return this;
		
	}
	public BST_Node findMax(){
		if(right!=null){
			return right.findMax();
		}
		return this;
		
	}
	public int getHeight(){
		int l=0;
		int r=0;
		if(left!=null)l+=left.getHeight()+1;
		if(right!=null)r+=right.getHeight()+1;
		return Integer.max(l, r);
	}
	public String toString(){
		return "Data: "+this.data+", Left: "+((this.left!=null)?left.data:"null")+",Right: "+((this.right!=null)?right.data:"null");
	}
  
	public BST_Node splay(BST_Node toSplay){
		if(toSplay.par == null){
			return toSplay;
		}
		else{
			parent = toSplay.getPar();
			grand = parent.getPar();
		}
		
		if(grand == null){ //zig
			if(parent.getLeft() == toSplay){ 
				//rotateRight(parent);
				makeLeftChildParent(toSplay, parent);
				return toSplay;
			}
			if(parent.getRight() == toSplay){
				//rotateLeft(parent);
				makeRightChildParent(toSplay, parent);
				return toSplay;
			}
		}
		else{
			if(grand.getLeft() == parent ){
				if(parent.getLeft() == toSplay){ //zig-zig a left b left c
					//rotateRight(grand);
					//rotateRight(parent);
					makeLeftChildParent(parent,grand);
					makeLeftChildParent(toSplay,parent);
					if(toSplay.getPar()!= null){
						splay(toSplay);
					}
					return toSplay;
				}
				if(parent.getRight() == toSplay){ //zig-zag a left b right c
					//rotateLeft(parent);
					//rotateRight(grand);
					makeRightChildParent(toSplay, parent);
					makeLeftChildParent(toSplay, grand);
					if(toSplay.getPar()!= null){
						splay(toSplay);
					}
					return toSplay;
				}
			}
			if(grand.getRight() == parent){
				if(parent.getLeft() == toSplay){ //zig-zag a right b left c
					//rotateRight(parent);
					//rotateLeft(grand);
					makeLeftChildParent(toSplay, parent);
					makeRightChildParent(toSplay, grand);
					if(toSplay.getPar()!= null){
						splay(toSplay);
					}
					return toSplay;
				}
				if(parent.getRight() == toSplay){ //zig-zig a right b right c
					//rotateLeft(grand);
					//rotateLeft(parent);
					makeRightChildParent(parent,grand);
					makeRightChildParent(toSplay,parent);
					if(toSplay.getPar()!= null){
						splay(toSplay);
					}
					return toSplay;
				}
			}
			
		}
		
		return toSplay;	
	}
    public void makeLeftChildParent(BST_Node c, BST_Node p){
      if ((c == null) || (p == null) || (p.left != c) || (c.par != p)){
    	  if(c == null){
    		  throw new RuntimeException("c is null");
    	  }
    	  if(p == null){
    		  throw new RuntimeException("p is null");
    	  }
    	  if(p.left != c){
    		  throw new RuntimeException("p.left not c");
    	  }
    	  if(c.par !=  p){
    		  throw new RuntimeException("c.par not p");
    	  }
      }

      if (p.par != null)
      {
          if (p == p.par.left){
        	  
          
              p.par.left = c;
          }
          else {
              p.par.right = c;
          }
      }
      if (c.right != null){
    	  c.right.par = p;
      }

      c.par = p.par;
      p.par = c;
      p.left = c.right;
      c.right = p;
  }
  public void makeRightChildParent(BST_Node c, BST_Node p)
  {
      if ((c == null) || (p == null) || (p.right != c) || (c.par != p))
          throw new RuntimeException("WRONG");
      if (p.par != null)
      {
          if (p == p.par.left)
              p.par.left = c;
          else
              p.par.right = c;
      }
      if (c.left != null)
          c.left.par = p;
      c.par = p.par;
      p.par = c;
      p.right = c.left;
      c.left = p;
  }

  
  
}