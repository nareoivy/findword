import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
/*
 * �ҳ���ĸͼƬD:/word.bmp �е�����Ӣ�ĵ���
 * 
 * ��Χ��Ŀ�꣺��ͼƬ�Ͻ����ʱ�ע����
 * 
 * */
class FindWord{
	private static final int maxLength=15;
	private static char[][] arr=new char[maxLength][maxLength];//
	
	private static char[][] arr2=new char[maxLength][maxLength];//��arr��ת������������
	private static char[][] arr3=new char[maxLength][maxLength];//������
	private static char[][] arr4=new char[maxLength][maxLength];//������
	
	private static Trie trieDict=null;//trie���ֵ�
	public static void main(String ardds[]){
		/*
		 * Part One
		*/
		String imgPath="D:/word.bmp";//ͼ����>txt
		img2txt(imgPath);
		/*
		 * Part Two
		*/
		String txtPath="D:/tmp.txt";//txt����>����
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
	
	//����tessertactʶ��ͼƬ
	public static void img2txt(String imgPath){//��ʶ�����浽D:/tmp.txt��
		String  command="tesseract "+imgPath+" D:/tmp";
		try{
			Runtime.getRuntime().exec(command);
		}catch(Exception e){
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
	}
	public static void txt2arr(String filePath){//���ļ��浽����,���ں������
		File file=new File(filePath);
		BufferedReader br=null;
		try{
			br=new BufferedReader(new FileReader(file));
			String str="";
			int line=0;
			while((str=br.readLine())!=null){//���һ���и�"�ջ���"��Ҳ��������
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
		for(int i=0;i<maxLength;i++){//��arr����ת�á�----��arr�ĵ�һ�У����arr2�ĵ�һ��
			for(int j=0;j<maxLength;j++){
				arr2[j][i]=arr[i][j];
			}
		}
		//arr3
		for(int i=0;i<maxLength;i++){//��arr�������н��з�ת
			for(int j=0;j<maxLength;j++){
				arr3[i][maxLength-1-j]=arr[i][j];
			}
		}
		//arr4
		for(int i=0;i<maxLength;i++){//��arr2�������н��з�ת,��
			for(int j=0;j<maxLength;j++){
				arr4[i][maxLength-1-j]=arr2[i][j];
			}
		}
	}
	/*
	 * ����ļ�����������ʼ��Ч�ˣ�
	 * �ȴ���У����
	 * */
	
	//����ָ���ַ����к�����Щ����
	//str:�������ַ���
	//location:���꣨�Ǻ����껹��������ȡ����char xy��ֵ��
	//xy:��ǰ������л�����
	//��������������ȫ��Ϊ��������Ѻ�
	public static void check1str(String str,int location,char xy){
		boolean flag=false;//false����û���ѵ�
		int len=str.length();
		int i,j;
		String tmp="";
		for(i=0;i<len;i++){
			for(j=i+3;j<=len;j++){//�鿴����������ĸ��ɵĵ��ʣ�
				tmp=str.substring(i, j);//ȡ�Ӵ�������i~(j-1)
				//System.out.println(tmp);
				if(trieDict.has(tmp)){
					flag=true;
					switch(xy)
					{
					case 'x'://��
						System.out.println("("+(i+1)+","+(location+1)+")"+"--->"+"("+j+","+(location+1)+"):"+tmp);
						break;
						
					case 'y'://��
						System.out.println("("+(location+1)+","+(i+1)+")"+"--->"+"("+(location+1)+","+j+"):"+tmp);
						break;
					case 'm'://����
						System.out.println("("+(maxLength-i)+","+(location+1)+")"+"--->"+"("+(maxLength+1-j)+","+(location+1)+"):"+tmp);
						break;
					case 'n'://����
						System.out.println("("+(location+1)+","+(maxLength-i)+")"+"--->"+"("+(location+1)+","+(maxLength+1-j)+"):"+tmp);
						break;
					}
					
				}
			}
		}
		//if(!flag){
			//System.out.println(str+"�У�û�е���");
		//}
	}
	
	//������������
	//ֱ�Ӷ�ȫ������arr����
	public static void checkAll(){
		int row,col;
		//��
		System.out.println("�У�");
		for(row=0;row<maxLength;row++){
			String str=String.valueOf(arr[row]);
			check1str(str,row,'x');
		}
				
		//��
		System.out.println("�У�");
		for(col=0;col<maxLength;col++){
			String str=String.valueOf(arr2[col]);
			check1str(str,col,'y');
		}
		
		//������
		System.out.println("������");
		for(row=0;row<maxLength;row++){
			String str=String.valueOf(arr3[row]);
			check1str(str,row,'m');
		}
		//������
		System.out.println("������ ��");
		for(col=0;col<maxLength;col++){
			String str=String.valueOf(arr4[col]);
			check1str(str,col,'n');
		}
	}
	
		
}