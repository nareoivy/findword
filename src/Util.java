import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * 工具类
 * 主要是将下载的词典进行处理，去掉音标、注释等内容
 * */
public class Util { 
	static Trie tree=new Trie();
	public static void main(String args[]){
		String inputPath="D:/tmpDict.txt";
		String dictPath="D:/dict.txt";//生成字典的保存位置
		String dicTrieObjPath="D:/dicTrieObj";
		//dealDict(inputPath,dictPath);//生成一遍后，即可注释掉，然后去人工修改一下dict
		dictSerial(dictPath,dicTrieObjPath);
	}
	//针对str="12 the [e?, ei:] art.这，那 ad.[用于比较级；最高级前]";   提取出其中的the
	public static String dealDict(String str){
		String tmpStr="";
		Pattern p = Pattern.compile("\\d+(.*?)\\[");
	    Matcher m = p.matcher(str);
	    //while(m.find()) {
	    if(m.find()){
	    	tmpStr=m.group(1);
	    	tmpStr=tmpStr.trim();
	        System.out.println(tmpStr);
	    }
	    return tmpStr;
	}
	//overload重载上方法，处理文件
	public static  void dealDict(String inputPath,String outputPath){
		File file =new File(inputPath);
		BufferedReader br=null;
		FileWriter fw=null;
		try{
			br=new BufferedReader(new FileReader(file));
			fw=new FileWriter(outputPath);
			String str="";
			String str2write="";
			int line=0;
			while((str=br.readLine())!=null){
				str2write=Util.dealDict(str)+"\r\n";//调用上一个同名方法
				//System.out.println(str2write);
				fw.write(str2write);
				line++;
			}
			//System.out.println(line);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			if(br!=null){
				try{
					br.close();
				}catch(Exception e){
					System.out.println(e.getMessage());
					//e.printStackTrace();
				}
			}
			if(fw!=null){
				try{
					fw.close();
				}catch(Exception e){
					System.out.println(e.getMessage());
					//e.printStackTrace();
				}
			}
		}
	}
	
	
	/*
	 * 操纵trie树，将字典存入trie树的一个对象，并将该对象存到文件中（对象序列化）
	 * 以后运行，可直接读取对象
	 * */
	public static void dictSerial(String dictPath,String dictTrieObjPath){
		
		File file =new File(dictPath);
		BufferedReader br=null;
		ObjectOutputStream oos=null;
		try{
			br=new BufferedReader(new FileReader(file));
			oos=new ObjectOutputStream(new FileOutputStream(dictTrieObjPath));
			String str="";
			int line=0;
			//将词典内容插入trie树
			while((str=br.readLine())!=null){
				tree.insert(str);
				System.out.println(line+"---->"+str);
				line++;
			}
			//序列化trie树后保存
			//System.out.println(tree.has("popular"));
			oos.writeObject(tree);
			System.out.println("序列化成功");
		}catch(Exception e){
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}finally{
			if(br!=null){
				try{
					br.close();
				}catch(Exception e){
					//System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
			if(oos!=null){
				try{
					oos.close();
				}catch(Exception e){
					//System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Trie dictDeserial(String dictTrieObjPath){
		ObjectInputStream ois=null;
		Trie tmpTrie=null;
		try{
			ois=new ObjectInputStream(new FileInputStream(dictTrieObjPath));
			tmpTrie=(Trie)ois.readObject();
		}catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
				if(ois!=null){
					ois.close();
				}
			}catch(IOException e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return  tmpTrie;
	}
	
	
	
}
