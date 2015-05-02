package cognizant.nayan.serialized;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import cognizant.nayan.commons.NayanProperties;
import cognizant.nayan.commons.RunType;
import cognizant.nayan.io.IO.Paths;

/**
 * @author 416474
 *
 * @param <T>
 */
public class ReadObject<T> {
	T obj;
	private String mType;
	private static NayanProperties mVersion = new NayanProperties();
	
	public ReadObject(RunType paramType) {
		System.out.println(paramType.toString());
		this.mType = paramType.toString();
	}
	
	@SuppressWarnings("unchecked")
	public T readObjectState(String filename){
		String brandname = mVersion.getBrandName();
		FileInputStream fIn = null;
		ObjectInputStream in = null;
		try{
			fIn = new FileInputStream(Paths.OBJECT_STATE_FOLDER+File.separator+brandname+File.separator+mType+File.separator+filename+".ser");
			in = new ObjectInputStream(fIn);
			obj = (T)in.readObject();
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
}
