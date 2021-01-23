package net.foreverdevs.utils.io;

import net.foreverdevs.utils.iteration.ArrayUtils;

import java.io.IOException;
import java.io.OutputStream;

public class MultipleOutputStream extends OutputStream {
	
	OutputStream[] streams;
	
	public MultipleOutputStream(OutputStream...streams) {
		this.streams = streams;
	}
	
	public void addStreams(OutputStream...streams) {
		int ol = this.streams.length;
		streams = ArrayUtils.expand(this.streams,streams.length);
		for(int i = 0;i<streams.length;i++)this.streams[ol+i] = streams[i];
	}
	
	@Override
	public void write(int b) throws IOException {
		for(OutputStream s : streams)s.write(b);
	}

}
