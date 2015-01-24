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
 * ������
 * ��Ҫ�ǽ����صĴʵ���д���ȥ�����ꡢע�͵�����
 * */
public class Util { 
	static Trie tree=new Trie();
	public static void main(String args[]){
		String inputPath="D:/tmpDict.txt";
		String dictPath="D:/dict.txt";//�����ֵ�ı���λ��
		String dicTrieObjPath="D:/dicTrieObj";
		//dealDict(inputPath,dictPath);//����һ��󣬼���ע�͵���Ȼ��ȥ�˹��޸�һ��dict
		dictSerial(dictPath,dicTrieObjPath);
	}
	//���str="12 the [e?, ei:] art.�⣬�� ad.[���ڱȽϼ�����߼�ǰ]";   ��ȡ�����е�the
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
	//overload�����Ϸ����������ļ�
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
				str2write=Util.dealDict(str)+"\r\n";//������һ��ͬ������
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
	 * ����trie�������ֵ����trie����һ�����󣬲����ö���浽�ļ��У��������л���
	 * �Ժ����У���ֱ�Ӷ�ȡ����
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
			//���ʵ����ݲ���trie��
			while((str=br.readLine())!=null){
				tree.insert(str);
				System.out.println(line+"---->"+str);
				line++;
			}
			//���л�trie���󱣴�
			//System.out.println(tree.has("popular"));
			oos.writeObject(tree);
			System.out.println("���л��ɹ�");
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
