package objetosbinarios;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class OOSSinCabecera extends ObjectOutputStream {

	public OOSSinCabecera(OutputStream salida) throws IOException {
		super(salida);
	}
	
	@Override
	protected void writeStreamHeader() throws IOException {
		// Sin cabecera
	}
}
