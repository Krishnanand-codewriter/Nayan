package sampleTests;

import java.io.File;

import org.junit.Test;

import cognizant.nayan.commons.NayanProperties;
import cognizant.nayan.commons.RunType;
import cognizant.nayan.serialized.ImagesState;
import cognizant.nayan.serialized.ReadObject;
import cognizant.nayan.serialized.WriteObject;

public class ObjectTest {

	@Test
	public void test() {
		ImagesState state = new ImagesState();
		state.addtoImageFiles(new File("Hello"));
		NayanProperties prop = new NayanProperties();
		prop.setFilename("Play");
		String fileName = prop.getFileName("Play");
		WriteObject<ImagesState> obj = new WriteObject<>(state, RunType.BASE);
		obj.writeObjectState(fileName);
		ReadObject<ImagesState> obj1 = new ReadObject<>(RunType.BASE);
		ImagesState obj2 = obj1.readObjectState(fileName);
		System.out.println(obj2.getImageFiles());
	
	}

}
