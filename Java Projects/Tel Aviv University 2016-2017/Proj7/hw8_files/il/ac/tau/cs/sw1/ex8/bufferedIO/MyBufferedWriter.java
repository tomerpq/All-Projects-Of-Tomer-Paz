
package il.ac.tau.cs.sw1.ex8.bufferedIO;

import java.io.FileWriter;
import java.io.IOException;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class MyBufferedWriter implements IBufferedWriter{
	private char[] buffer;
	private int bufferSize, /*empty buffer:*/ bufferPlaceTaken = 0;
	private FileWriter fWriter;
	private int DEFAULT_BUFFER_SIZE = 8192;
	
	public MyBufferedWriter(FileWriter fWriter, int bufferSize){
		this.fWriter = fWriter;
		if(bufferSize <= 0){
			this.buffer = new char[DEFAULT_BUFFER_SIZE];
			this.bufferSize = DEFAULT_BUFFER_SIZE;
		}
		else{
			this.buffer = new char[bufferSize];
			this.bufferSize = bufferSize;
		}
	}

	
	@Override
	public void write(String str) throws IOException {
		int st = 0;
		int length = str.length();
		char[] strArr = str.toCharArray();
		if(bufferPlaceTaken > 0){
			int empty = bufferSize - bufferPlaceTaken;
			if(length >= empty){
				fWriter.write(buffer,0,bufferPlaceTaken);
				fWriter.write(strArr,st,empty);
				length -= empty;
				st += empty; 
				bufferPlaceTaken = 0;
			}
			else{
				while(length > 0){
					buffer[bufferPlaceTaken] = strArr[st];
					length --;
					st ++;
					bufferPlaceTaken ++;
				}
				return;
			}
		}
		while(length >= bufferSize){
			fWriter.write(strArr,st,bufferSize);
			length -= bufferSize;
			st += bufferSize;
		}
		for(int i = 0; i < length; i++){
			buffer[i] = strArr[st];
			st ++;
			bufferPlaceTaken ++;
		}
	}
	
	@Override
	public void close() throws IOException {
		if(bufferPlaceTaken > 0){
			fWriter.write(buffer,0,bufferPlaceTaken);
			bufferPlaceTaken = 0;
		}
		fWriter.close();
	}
}
