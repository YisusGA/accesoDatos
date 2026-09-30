package es.dam2.accesoFicheros;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectOutputStreamNoHeader extends ObjectOutputStream {
	
	public ObjectOutputStreamNoHeader (OutputStream i) throws IOException {
		super(i);
	}
	
	@Override
	public void writeStreamHeader() {
		
	}

}
