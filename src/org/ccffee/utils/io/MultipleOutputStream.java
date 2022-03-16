package org.ccffee.utils.io;


import org.ccffee.utils.iteration.ArrayUtils;

import java.io.IOException;
import java.io.OutputStream;

public class MultipleOutputStream extends OutputStream {
	
	OutputStream[] streams;
	
	public MultipleOutputStream(OutputStream...streams) {
		this.streams = streams;
	}
	
	public void addStreams(OutputStream...streams) {
		this.streams = ArrayUtils.addArrayAndExpand(this.streams,streams);
	}
	public void addStream(OutputStream stream){
		streams = ArrayUtils.addAndExpand(streams,stream);
	}
	@Override
	public void write(int b) throws IOException {
		for(OutputStream s : streams)s.write(b);
	}

}
