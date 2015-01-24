import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
/*
 * 找出字母图片D:/word.bmp 中的所有英文单词
 * 
 * 范围外目标：在图片上将单词标注出来
 * 
 * */
class FindWord{
	private static final int maxLength=15;
	private static char[][] arr=new char[maxLength][maxLength];//
	
	private static char[][] arr2=new char[maxLength][maxLength];//将arr做转置运算后的数组
	private static char[][] arr3=new char[maxLength][maxLength];//行逆序
	private static char[][] arr4=new char[maxLength][maxLength];//列逆序
	
	private static Trie trieDict=null;//trie树字典
	public static void main(String ardds[]){
		/*
		 * Part One
		*/
		String imgPath="D:/word.bmp";//图——>txt
		img2txt(imgPath);
		/*
		 * Part Two
		*/
		String txtPath="D:/tmp.txt";//txt——>数组
		txt2arr(txtPath);
		arr2arr234();
		/*
		 * Part Three
		*/
		String dictTrieObjPath="D:/dicTrieObj";
		trieDict=Util.dictDeserial(dictTrieObjPath);
		//trieDict.preTraverse(trieDict.getRoot());
		//System.out.println(trieDict.has("popular"));
		//System.out.println(arr2[14]);
		//String str=String.valueOf(arr[14]);
		//check1str(str);
		checkAll();
		//System.out.println(arr2[14]);
		//System.out.println(arr3[14]);
		//System.out.println(arr4[14]);
		
	}
	
	//调用tessertact识别图片
	public static void img2txt(String imgPath){//将识别结果存到D:/tmp.txt中
		String  command="tesseract "+imgPath+" D:/tmp";
		try{
			Runtime.getRuntime().exec(command);
		}catch(Exception e){
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
	}
	public static void txt2arr(String filePath){//读文件存到数组,便于后面分析
		File file=new File(filePath);
		BufferedReader br=null;
		try{
			br=new BufferedReader(new FileReader(file));
			String str="";
			int line=0;
			while((str=br.readLine())!=null){//最后一行有个"空换行"，也读进来了
				str=str.toLowerCase();
				for(int key=0;key<str.length();key++)
					arr[line][key]=str.charAt(key);	
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
		}
		
	}
	public static void arr2arr234(){
		//arr2
		for(int i=0;i<maxLength;i++){//将arr做“转置”----将arr的第一列，变成arr2的第一行
			for(int j=0;j<maxLength;j++){
				arr2[j][i]=arr[i][j];
			}
		}
		//arr3
		for(int i=0;i<maxLength;i++){//把arr的所有行进行反转
			for(int j=0;j<maxLength;j++){
				arr3[i][maxLength-1-j]=arr[i][j];
			}
		}
		//arr4
		for(int i=0;i<maxLength;i++){//把arr2的所有行进行反转,即
			for(int j=0;j<maxLength;j++){
				arr4[i][maxLength-1-j]=arr2[i][j];
			}
		}
	}
	/*
	 * 下面的几个函数，开始低效了！
	 * 等待高校做法
	 * */
	
	//搜索指定字符串中含有那些单词
	//str:待检测的字符串
	//location:坐标（是横坐标还是纵坐标取决于char xy的值）
	//xy:当前正检查行或者列
	//后面两个参数完全是为了输出更友好
	public static void check1str(String str,int location,char xy){
		boolean flag=false;//false代表没有搜到
		int len=str.length();
		int i,j;
		String tmp="";
		for(i=0;i<len;i++){
			for(j=i+3;j<=len;j++){//查看三个以上字母组成的单词！
				tmp=str.substring(i, j);//取子串，索引i~(j-1)
				//System.out.println(tmp);
				if(trieDict.has(tmp)){
					flag=true;
					switch(xy)
					{
					case 'x'://行
						System.out.println("("+(i+1)+","+(location+1)+")"+"--->"+"("+j+","+(location+1)+"):"+tmp);
						break;
						
					case 'y'://列
						System.out.println("("+(location+1)+","+(i+1)+")"+"--->"+"("+(location+1)+","+j+"):"+tmp);
						break;
					case 'm'://行逆
						System.out.println("("+(maxLength-i)+","+(location+1)+")"+"--->"+"("+(maxLength+1-j)+","+(location+1)+"):"+tmp);
						break;
					case 'n'://列逆
						System.out.println("("+(location+1)+","+(maxLength-i)+")"+"--->"+"("+(location+1)+","+(maxLength+1-j)+"):"+tmp);
						break;
					}
					
				}
			}
		}
		//if(!flag){
			//System.out.println(str+"中，没有单词");
		//}
	}
	
	//搜索整个矩阵
	//直接对全局数组arr操作
	public static void checkAll(){
		int row,col;
		//行
		System.out.println("行：");
		for(row=0;row<maxLength;row++){
			String str=String.valueOf(arr[row]);
			check1str(str,row,'x');
		}
				
		//列
		System.out.println("列：");
		for(col=0;col<maxLength;col++){
			String str=String.valueOf(arr2[col]);
			check1str(str,col,'y');
		}
		
		//行逆序
		System.out.println("行逆序：");
		for(row=0;row<maxLength;row++){
			String str=String.valueOf(arr3[row]);
			check1str(str,row,'m');
		}
		//列逆序
		System.out.println("列逆序 ：");
		for(col=0;col<maxLength;col++){
			String str=String.valueOf(arr4[col]);
			check1str(str,col,'n');
		}
	}
	
		
}